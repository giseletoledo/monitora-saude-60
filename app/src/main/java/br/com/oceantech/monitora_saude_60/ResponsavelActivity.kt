package br.com.oceantech.monitora_saude_60

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityResponsavelBinding
import br.com.oceantech.monitora_saude_60.model.Responsavel
import br.com.oceantech.monitora_saude_60.viewModel.ResponsavelViewModel
import com.google.android.material.appbar.MaterialToolbar

class ResponsavelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResponsavelBinding
    private lateinit var viewModel: ResponsavelViewModel
    private var responsavelId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResponsavelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        responsavelId = intent.getLongExtra("responsavel_id", -1L)
        viewModel = ViewModelProvider(this).get(ResponsavelViewModel::class.java)

        if (responsavelId != -1L) {
            // Edit Mode
            viewModel.getResponsavelById(responsavelId).observe(this, { responsavel ->
                populateFields(responsavel)
            })
        }

        binding.btnRespEditar.text = if (responsavelId != -1L) {
            // Edit Mode
            "Editar Responsável"
        } else {
            // Add Mode
            "Cadastrar Responsável"
        }

        binding.btnRespEditar.setOnClickListener {
            val nome = binding.nomeRespInputEditText.text.toString().trim()
            val celular = binding.celularInputEditText.text.toString().trim()
            if (isValidInputs(nome, celular)) {
                if (nome.isNotEmpty() && celular.isNotEmpty()) {
                    val responsavel = Responsavel(celular, nome)

                    if (responsavelId != null) {
                        // Edit Mode
                        viewModel.updateResponsavel(responsavel)
                    } else {
                        // Add Mode
                        viewModel.insertResponsavel(responsavel)
                    }

                    finish()
                } else {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidInputs(nome: String, celular: String): Boolean {
        if (nome.length > 60) {
            Toast.makeText(this, "O nome excede o limite de caracteres", Toast.LENGTH_SHORT).show()
            return false
        }

        val celularPattern = "^\\(\\d{2}\\) 9\\d{4}-\\d{4}\$"
        if (!celular.matches(celularPattern.toRegex())) {
            Toast.makeText(this, "Formato inválido do número de celular", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun populateFields(responsavel: Responsavel) {
        binding.nomeRespInputEditText.setText(responsavel.nome)
        binding.celularInputEditText.setText(responsavel.celular)

        // Atualiza o valor de responsavelId para o número de celular atual
        responsavelId = responsavel.celular.toLong()
    }
}

