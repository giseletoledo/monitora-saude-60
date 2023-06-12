package br.com.oceantech.monitora_saude_60


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.oceantech.monitora_saude_60.databinding.ActivityMainBinding
import br.com.oceantech.monitora_saude_60.model.HorarioMedicamento
import br.com.oceantech.monitora_saude_60.model.User
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoViewModel
import br.com.oceantech.monitora_saude_60.viewModel.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var medicamentoViewModel: MedicamentoViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var horarioAdapter: HorarioAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medicamentoViewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val textViewUserName: TextView = findViewById(R.id.textViewUserName)

        userViewModel.users.observe(this) { users: List<User> ->
            if (users.isNotEmpty()) {
                val lastUser = users.last() // Get the last user in the list
                val greeting = "Olá, ${lastUser.name}"
                textViewUserName.text = greeting
            }
        }

        lifecycleScope.launch {
            userViewModel.getAllUsers()
        }

        // Configura o RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewHorarios)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horarioAdapter = HorarioAdapter(emptyList(), emptyList())
        recyclerView.adapter = horarioAdapter

        medicamentoViewModel.recentHorarios.observe(this) { horarios: List<HorarioMedicamento> ->
            val nomes = horarios.map { it.nome }
            val horas = horarios.map { it.hora }

            horarioAdapter.updateData(horas, nomes)
        }

        lifecycleScope.launch {
            medicamentoViewModel.getRecentHorarios()
        }

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
        //medicamentoViewModel.getRecentHorarios() // Atualiza os horários recentes ao entrar na tela
    }
}