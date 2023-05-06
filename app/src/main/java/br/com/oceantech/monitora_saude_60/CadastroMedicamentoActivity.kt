package br.com.oceantech.monitora_saude_60

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityCadastroMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.utils.toHorariosListOrNull
import br.com.oceantech.monitora_saude_60.utils.toLocalDateOrNull
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
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
            viewModel.getById(medicamentoId.toInt()).observe(this, { medicamento ->
                if (medicamento != null) {
                    preencherFormulario(medicamento)
                }
            })
        }

        binding.btnSalvarMedicamento.setOnClickListener {
            val nome = binding.nomeMedicamento.text.toString().trim()
            val dosagem = binding.dosagem.text.toString().toDoubleOrNull()
            val intervaloDoses = binding.intervaloDoses.text.toString().toIntOrNull()
            val dataInicio = binding.txtDatainicial.editText.toString().toLocalDateOrNull()
            val dataFim = binding.txtDatafinal.editText.toString().toLocalDateOrNull()
            val horarios = binding.txtHorarios.editText.toString().toHorariosListOrNull()

            if (nome.isBlank() || dosagem == null || intervaloDoses == null || dataInicio == null || dataFim == null || horarios == null) {
                Snackbar.make(binding.root, R.string.msg_campos_invalidos, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val medicamento = Medicamento(
                medicamentoId.toInt(),
                nome,
                dosagem,
                intervaloDoses,
                dataInicio,
                dataFim,
                horarios
            )

            viewModel.insert(medicamento)
            setResult(Activity.RESULT_OK)
            finish()
        }
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