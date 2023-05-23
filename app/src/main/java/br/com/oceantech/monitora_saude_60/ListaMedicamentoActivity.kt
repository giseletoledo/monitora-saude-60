package br.com.oceantech.monitora_saude_60

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.oceantech.monitora_saude_60.databinding.ActivityListaMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoListAdapter
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoViewModel
import com.google.android.material.appbar.MaterialToolbar

class ListaMedicamentoActivity : AppCompatActivity() {

        private lateinit var binding: ActivityListaMedicamentoBinding
        private lateinit var adapter: MedicamentoListAdapter
        private lateinit var viewModel: MedicamentoViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityListaMedicamentoBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Configura a Toolbar
            val toolbar: MaterialToolbar = binding.toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.fabAdicionarMedicamento.setOnClickListener {
                val intent = Intent(this, CadastroMedicamentoActivity::class.java)
                startActivity(intent)
            }

            // Inicialize a RecyclerView e o Adapter
            adapter = MedicamentoListAdapter(onDelete = { medicamento -> deleteMedicamento(medicamento) }, onEdit = { medicamento ->
                editMedicamento(
                    medicamento
                )
            })

            binding.recyclerViewMedicamento.adapter = adapter
            binding.recyclerViewMedicamento.layoutManager = LinearLayoutManager(this)

            // Crie uma instância da ViewModel
            viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

            // Observe as mudanças na lista de medicamentos e atualize o Adapter
            viewModel.medicamentos.observe(this) { medicamentos ->
                adapter.updateList(medicamentos)
            }

            // Observe as mudanças na lista de horários
            viewModel.horarios.observe(this) { horarios ->
                // Implemente a lógica para atualizar a lista de horários na UI aqui
            }

            // Carregue a lista de medicamentos do banco de dados
            viewModel.loadMedicamentos()

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

    private fun deleteMedicamento(medicamento: Medicamento) {
            viewModel.delete(medicamento)
        }
    private fun editMedicamento(medicamento: Medicamento) {
        val intent = Intent(this, EditarMedicamentoActivity::class.java)
        intent.putExtra(EditarMedicamentoActivity.EXTRA_MEDICAMENTO_ID, medicamento.id)
        startActivity(intent)
    }

}