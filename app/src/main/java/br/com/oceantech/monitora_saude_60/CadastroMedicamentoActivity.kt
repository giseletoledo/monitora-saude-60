package br.com.oceantech.monitora_saude_60

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityCadastroMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.utils.formatDate
import br.com.oceantech.monitora_saude_60.utils.text
import br.com.oceantech.monitora_saude_60.utils.toHorariosListOrNull
import br.com.oceantech.monitora_saude_60.utils.toLocalDateOrNull
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
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
            viewModel.getById(medicamentoId.toInt()).observe(this) { medicamento ->
                if (medicamento != null) {
                    preencherFormulario(medicamento)
                }
            }
        }

        insertListeners()

        binding.btnSalvarMedicamento.setOnClickListener {
            val nome = binding.nomeMedicamento.text.toString().trim()
            val dosagem = binding.dosagem.text.toString().toDoubleOrNull()
            val intervaloDoses = binding.intervaloDoses.text.toString().toIntOrNull()
            val dataInicio = binding.txtDatainicial.editText.toString().toLocalDateOrNull()
            val dataFim = binding.txtDatafinal.editText.toString().toLocalDateOrNull()
            val horarios = binding.txtHorarios.editText.toString().toHorariosListOrNull()

           /* if (nome.isBlank() || dosagem == null || intervaloDoses == null || dataInicio == null || dataFim == null || horarios == null) {

                Snackbar.make(binding.root, R.string.msg_campos_invalidos, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }*/

            Log.d("Medicamento", "Inserindo no banco de dados: $nome $dosagem $intervaloDoses $dataInicio $dataFim $horarios") // adiciona um log com os valores do medicamento

/*
            val medicamento = Medicamento(
                medicamentoId.toInt(),
                nome,
                dosagem!!,
                intervaloDoses!!,
                dataInicio!!,
                dataFim!!,
                horarios!!
            )*/

            //viewModel.insert(medicamento)
            //setResult(Activity.RESULT_OK)
            //finish()
        }
    }
    private fun insertListeners() {

        binding.txtDatainicial.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener { timestamp ->
                try {
                    val dataInicial = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
                    viewModel.onDataInicialSelecionada(dataInicial)
                    binding.txtDatainicial.text = dataInicial.formatDate()
                    validarDataInicial()
                } catch (e: DateTimeException) {
                    Toast.makeText(this, "Data inválida", Toast.LENGTH_LONG).show()
                }
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }


        binding.txtDatafinal.editText?.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener { timestamp ->
                try {
                    val dataFinal = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
                    val dataInicial = validarDataInicial()

                    viewModel.onDataFinalSelecionada(dataFinal)
                    binding.txtDatafinal.text = dataFinal.formatDate()
                    if (dataInicial != null) {
                        validarDataFinal(dataInicial)
                    }
                } catch (e: Exception) {
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
                val selectedTimeInMillis = timePicker.hour * 60L * 60L * 1000L + timePicker.minute * 60L * 1000L
                val intervaloDosesText = binding.intervaloDoses.text.toString()
                if (intervaloDosesText.isBlank() || intervaloDosesText.toInt() <= 0) {
                    Toast.makeText(this, "Intervalo de doses inválido", Toast.LENGTH_LONG).show()
                } else {
                    val intervaloDoses = intervaloDosesText.toInt()
                    val startDate = binding.txtDatainicial.editText?.text?.toString()?.toLocalDateOrNull()?.atStartOfDay()?.toInstant(ZoneOffset.UTC)?.toEpochMilli()

                    if (startDate == null) {
                        Toast.makeText(this, "Preencha a Data inicial", Toast.LENGTH_LONG).show()
                    } else {
                        val selectedTimeFormatted = "${timePicker.hour.toString().padStart(2, '0')}:${timePicker.minute.toString().padStart(2, '0')}"

                        binding.txtHorarios.editText?.setText(selectedTimeFormatted) ?: run {
                            Toast.makeText(this, "Preencha um horário", Toast.LENGTH_LONG).show()
                        }

                        val horarios = calcularHorarios(intervaloDoses, startDate, selectedTimeInMillis)
                        viewModel.onHorariosSelecionados(horarios as MutableList<String>)
                    }
                }
            }

            timePicker.show(supportFragmentManager, null)
        }
    }

    private fun calcularHorarios(intervaloDoses: Int, startDate: Long, selectedTimeInMillis: Long): List<String> {
        val horarios = mutableListOf<String>()
        var nextTimeInMillis = startDate + selectedTimeInMillis
        val intervalInMillis = intervaloDoses * 60 * 60 * 1000
        while (nextTimeInMillis < System.currentTimeMillis() + intervalInMillis) {
            val nextTime = Instant.ofEpochMilli(nextTimeInMillis).atZone(ZoneOffset.UTC).toLocalDateTime()
            val horario = "${nextTime.hour.toString().padStart(2, '0')}:${nextTime.minute.toString().padStart(2, '0')}"
            horarios.add(horario)
            nextTimeInMillis += intervalInMillis
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

    private fun validarDataFinal(dataInicial: LocalDate): LocalDate? {
        // Obtém a string contendo a data do campo de data final
        val dataFinalStr = binding.txtDatafinal.editText?.text?.toString()

        // Converte a string em um objeto LocalDate usando a função de extensão
        val dataFinal = dataFinalStr?.toLocalDateOrNull()

        // Verifica se a data final foi preenchida corretamente
        if (dataFinal == null) {
            // Exibe uma mensagem de erro ao usuário
            Toast.makeText(this, "Preencha a data final", Toast.LENGTH_LONG).show()
            return null
        }

        // Verifica se a data final é anterior à data inicial
        if (dataFinal < dataInicial) {
            // Exibe uma mensagem de erro ao usuário
            Toast.makeText(this, "Data final não pode ser anterior à data inicial", Toast.LENGTH_LONG).show()
            return null
        }

        return dataFinal
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tratar evento de clique no botão de voltar
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun preencherFormulario(medicamento: Medicamento) {
        binding.nomeMedicamento.setText(medicamento.nome)
        binding.dosagem.setText(medicamento.dosagem.toString())
        binding.intervaloDoses.setText(medicamento.intervaloDoses.toString())
        binding.txtDatainicial.editText?.setText(medicamento.dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE))
        binding.txtDatafinal.editText?.setText(medicamento.dataFim.format(DateTimeFormatter.ISO_LOCAL_DATE))
        binding.txtHorarios.editText?.setText(medicamento.horarios.joinToString(", "))
    }

    companion object {
        const val EXTRA_MEDICAMENTO_ID = "medicamento_id"
    }

}