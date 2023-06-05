package br.com.oceantech.monitora_saude_60

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.oceantech.monitora_saude_60.databinding.ActivityCadastroMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.utils.formatDate
import br.com.oceantech.monitora_saude_60.utils.toLocalDateOrNull
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class CadastroMedicamentoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroMedicamentoBinding
    private lateinit var viewModel: MedicamentoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroMedicamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)


        val medicamentoId = intent.getLongExtra(EXTRA_MEDICAMENTO_ID, -1)

        if (medicamentoId != -1L) {
            viewModel.getMedicamentoById(medicamentoId.toInt()).observe(this) { medicamento ->
                if (medicamento != null) {
                    preencherFormulario(medicamento)
                }
            }
        }

        viewModel.dataInicial.observe(this) { dataInicial ->
            binding.txtDatainicial.editText?.setText(dataInicial?.formatDate())
        }

        viewModel.horarios.observe(this) { horarios ->
            val firstHorario = horarios.firstOrNull()
            if (firstHorario != null) {
                binding.txtHorarios.editText?.setText(firstHorario.toString())
            }
        }

        insertListeners()

        binding.btnSalvarMedicamento.setOnClickListener {
            val nome = binding.nomeMedicamento.text.toString()
            val dosagem = binding.dosagem.text.toString().toDoubleOrNull() ?: 0.0
            val duracao = binding.duracao.text.toString().toIntOrNull()
            val intervaloDoses = binding.intervaloDoses.text.toString().toIntOrNull()
            val dataInicial = viewModel.dataInicial.value
            val horarios = viewModel.horarios.value ?: emptyList()

            if (nome.isBlank()  || intervaloDoses == null || dataInicial == null || duracao == null || horarios.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val medicamento = Medicamento(
                nome = nome,
                dosagem = dosagem,
                intervaloDoses = intervaloDoses,
                dataInicio = dataInicial,
                horarios = horarios,
                duracao = duracao,
            )
            lifecycleScope.launch {
                val medicamentos = viewModel.getMedicamentos()
                val medicamentosString = medicamentos.joinToString(separator = "\n")
                Log.d("Ver medicamentos cadastrados", medicamentosString)
                viewModel.insert(medicamento)
                showBottomSheetMessage("Medicamento, ${medicamento.nome} cadastrado")

            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    // Ação ao selecionar o menu Home
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_medicamentos -> {
                    // Direcionar para a ListaMedicamentoActivity
                    val intent = Intent(this, ListaMedicamentoActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_dieta -> {
                    // Ação ao selecionar o menu Dieta
                    true
                }
                R.id.action_relatorio -> {
                    // Ação ao selecionar o menu Relatório
                    true
                }
                R.id.action_configuracoes -> {
                    // Ação ao selecionar o menu Configurações
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.menu.findItem(R.id.action_medicamentos).isChecked = true
    }

    private fun showBottomSheetMessage(message: String) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_cadmed_message, null)
        val messageTextView = bottomSheetView.findViewById<TextView>(R.id.editarBottomTextView)
        messageTextView.text = message

        val btnEditarMedBottom = bottomSheetView.findViewById<MaterialButton>(R.id.btnEditarMedBottom)
        btnEditarMedBottom.text = getString(R.string.btn_finalizar_edt)

        btnEditarMedBottom.setOnClickListener {
            bottomSheetDialog.dismiss()
            finish()
        }

        bottomSheetDialog.setOnDismissListener {
            if (bottomSheetDialog.isShowing) {
                finish()
            }
        }

        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetDialog.show()
    }
    private fun insertListeners() {

        binding.txtDatainicial.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener { timestamp ->
                try {
                    val dataInicial = Instant.ofEpochMilli(timestamp)
                        .atZone(ZoneId.of("UTC")).toLocalDate()
                    viewModel.onDataInicialSelecionada(dataInicial)

                    validarDataInicial()
                    binding.txtDatainicial.editText?.setText(dataInicial.formatDate())
                } catch (e: DateTimeException) {
                    Toast.makeText(this, "Data inválida", Toast.LENGTH_LONG).show()
                }
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.txtHorarios.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                //val selectedTimeInMillis = timePicker.hour * 60L * 60L * 1000L + timePicker.minute * 60L * 1000L
                val intervaloDosesText = binding.intervaloDoses.text.toString()
                if (intervaloDosesText.isBlank() || intervaloDosesText.toInt() <= 0) {
                    Toast.makeText(this, "Intervalo de doses inválido", Toast.LENGTH_LONG).show()
                } else {
                    val intervaloDoses = intervaloDosesText.toInt()

                    val selectedTimeFormatted = "${timePicker.hour.toString().padStart(2, '0')}:${timePicker.minute.toString().padStart(2, '0')}"
                    binding.txtHorarios.editText?.setText(selectedTimeFormatted) ?: run {
                        Toast.makeText(this, "Preencha um horário", Toast.LENGTH_LONG).show()
                    }

                    val selectedTime = LocalTime.of(timePicker.hour, timePicker.minute)

                    val startDate = binding.txtDatainicial.editText?.text?.toString()?.toLocalDateOrNull()?.atTime(selectedTime.getHour(), selectedTime.getMinute())?.toInstant(ZoneOffset.UTC)
                    val duracaoDias = binding.duracao.text.toString().toIntOrNull() ?: 0

                    if (startDate == null || duracaoDias == null) {
                        Toast.makeText(this, "Preenchimento da Data inicial e Duração são obrigatórios ", Toast.LENGTH_LONG).show()
                    } else {
                        val horarios = calcularHorarios(intervaloDoses, startDate, selectedTimeFormatted, duracaoDias)
                        val localTimes = horarios.map { LocalTime.parse(it) }

                        viewModel.onHorariosSelecionados(localTimes)
                    }
                }
            }

            timePicker.show(supportFragmentManager, null)
        }
    }

    private fun calcularHorarios(intervaloDoses: Int, startDate: Instant?, selectedTimeFormatted: String, duracaoDias: Int): List<String> {
        val horarios = mutableListOf<String>()

        // Converter a string selectedTimeFormatted para LocalTime
        val selectedTime = LocalTime.parse(selectedTimeFormatted)

        // Converter a data de início para LocalDateTime
        val startDateTime = LocalDateTime.ofInstant(startDate, ZoneOffset.UTC)

        // Calcular a próxima data/hora do medicamento
        var nextDateTime = startDateTime.with(selectedTime)
        if (nextDateTime.isBefore(startDateTime)) {
            // Se a próxima data/hora do medicamento for antes da data de início, adicionar o intervalo
            nextDateTime = nextDateTime.plusHours(intervaloDoses.toLong())
        }
        val durationBetweenStartAndEnd = Duration.ofDays(duracaoDias.toLong())
        var currentDateTime = nextDateTime
        while (currentDateTime.isBefore(startDateTime.plus(durationBetweenStartAndEnd))) {
            val nextTimeFormatted = DateTimeFormatter.ISO_LOCAL_TIME.format(currentDateTime.toLocalTime())
            horarios.add(nextTimeFormatted)
            currentDateTime = currentDateTime.plusHours(intervaloDoses.toLong())
        }
        return horarios
    }

    private fun validarDataInicial(): LocalDate? {
        // Obtém a string contendo a data do campo de data inicial
        val dataInicialStr = binding.txtDatainicial.editText?.text?.toString()

        // Converte a string em um objeto LocalDate usando a função de extensão
        val dataInicial = dataInicialStr?.toLocalDateOrNull()

        // Verifica se a data inicial foi preenchida corretamente
        if (dataInicial == null) {
            // Exibe uma mensagem de erro ao usuário
            Toast.makeText(this, "Preencha a data inicial", Toast.LENGTH_LONG).show()
            return null
        }
        return dataInicial
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tratar evento de clique no botão de voltar
                onBackPressedDispatcher.onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun preencherFormulario(medicamento: Medicamento) {
        binding.nomeMedicamento.setText(medicamento.nome)
        binding.dosagem.setText(medicamento.dosagem.toString())
        binding.duracao.setText(medicamento.duracao.toString())
        binding.intervaloDoses.setText(medicamento.intervaloDoses.toString())
        binding.txtDatainicial.editText?.setText(medicamento.dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE))
        binding.txtHorarios.editText?.setText(medicamento.horarios.joinToString(", "))
    }

    companion object {
        const val EXTRA_MEDICAMENTO_ID = "medicamento_id"
    }

}

