package br.com.oceantech.monitora_saude_60.viewModel

import androidx.recyclerview.widget.RecyclerView
import br.com.oceantech.monitora_saude_60.databinding.ItemMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento

class MedicamentoViewHolder(private val binding: ItemMedicamentoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(medicamento: Medicamento, onDelete: (Medicamento) -> Unit, onEdit: (Medicamento) -> Unit) {
        binding.tvNomeMedicamento .text = medicamento.nome
        binding.tvDosagemMedicamento.text = medicamento.dosagem.toString()
        binding.tvIntervaloMedicamento.text = medicamento.intervaloDoses.toString()
        binding.tvHorariosMedicamento.text = medicamento.horarios.toString()

        binding.btnDeletar.setOnClickListener {
            onDelete(medicamento)
        }

        binding.btnEditar.setOnClickListener {
            onEdit(medicamento)
        }
    }
}

