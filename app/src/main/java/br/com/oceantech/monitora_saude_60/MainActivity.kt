package br.com.oceantech.monitora_saude_60


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.oceantech.monitora_saude_60.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMenuMedicamentos.setOnClickListener {
            val intent = Intent(this, ListaMedicamentoActivity::class.java)
            startActivity(intent)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    // Ação ao selecionar o menu Home
                    val intent = Intent(this, VisitanteActivity::class.java)
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
        binding.bottomNavigationView.menu.findItem(R.id.action_home).isChecked = true
    }
}