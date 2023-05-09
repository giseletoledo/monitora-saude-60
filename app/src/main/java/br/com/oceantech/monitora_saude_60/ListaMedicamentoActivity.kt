package br.com.oceantech.monitora_saude_60

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.oceantech.monitora_saude_60.databinding.ActivityListaMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoListAdapter
import br.com.oceantech.monitora_saude_60.viewModel.MedicamentoViewModel

class ListaMedicamentoActivity : AppCompatActivity() {

        private lateinit var binding: ActivityListaMedicamentoBinding
        private lateinit var adapter: MedicamentoListAdapter
        private lateinit var viewModel: MedicamentoViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityListaMedicamentoBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Inicialize a RecyclerView e o Adapter
            adapter = MedicamentoListAdapter(this, onDelete = { medicamento -> deleteMedicamento(medicamento) }, onEdit = { medicamento ->
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
        }

        private fun deleteMedicamento(medicamento: Medicamento) {
            viewModel.delete(medicamento)
        }

        private fun editMedicamento(medicamento: Medicamento) {
            // Implemente a lógica de edição de medicamento aqui
        }
}