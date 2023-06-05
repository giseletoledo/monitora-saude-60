package br.com.oceantech.monitora_saude_60

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityConfiguracaoBinding
import br.com.oceantech.monitora_saude_60.viewModel.ResponsavelViewModel
import com.google.android.material.appbar.MaterialToolbar

class ConfiguracaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracaoBinding
    private lateinit var viewModel: ResponsavelViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguracaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(ResponsavelViewModel::class.java)
        viewModel.allResponsaveis.observe(this) { responsaveis ->
            if (responsaveis.isNotEmpty()) {
                val ultimoResponsavel = responsaveis.last()
                binding.nomeResp.text = ultimoResponsavel.nome
                binding.celularResp.text = ultimoResponsavel.celular
            }
        }

        binding.btnResponsavel.setOnClickListener {
            val intent = Intent(this, ResponsavelActivity::class.java)
            startActivity(intent)
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
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_configuracoes -> {
                    // Ação ao selecionar o menu Configurações
                    val intent = Intent(this, ConfiguracaoActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.menu.findItem(R.id.action_configuracoes).isChecked = true
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
}