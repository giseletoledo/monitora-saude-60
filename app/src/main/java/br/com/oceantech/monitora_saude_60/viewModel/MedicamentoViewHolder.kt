package br.com.oceantech.monitora_saude_60.viewModel

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import br.com.oceantech.monitora_saude_60.databinding.ItemMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento

class MedicamentoViewHolder(private val binding: ItemMedicamentoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(medicamento: Medicamento, onDelete: (Medicamento) -> Unit, onEdit: (Medicamento) -> Unit) {

        binding.tvNomeMedicamento.text = "Nome do remédio: ${medicamento.nome}"
        binding.tvDosagemMedicamento.text = "Dosagem: ${medicamento.dosagem}"
        binding.tvIntervaloMedicamento.text = "Intervalo entre doses: ${medicamento.intervaloDoses}"
        binding.tvHorariosMedicamento.text = "Horários: ${medicamento.horarios.joinToString(", ")}"

        binding.btnDeletar.setOnClickListener {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setMessage("Tem certeza que deseja excluir este medicamento?")
                .setCancelable(false)
                .setPositiveButton("Sim") { _, _ ->
                    onDelete(medicamento)
                }
                .setNegativeButton("Não") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        binding.btnEditar.setOnClickListener {
            onEdit(medicamento)
        }
    }
}

