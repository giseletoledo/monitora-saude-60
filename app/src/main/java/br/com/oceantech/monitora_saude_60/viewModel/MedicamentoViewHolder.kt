package br.com.oceantech.monitora_saude_60.viewModel

import android.provider.Settings.Secure.getString
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import br.com.oceantech.monitora_saude_60.R
import br.com.oceantech.monitora_saude_60.databinding.ItemMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento

class MedicamentoViewHolder(private val binding: ItemMedicamentoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(medicamento: Medicamento, onDelete: (Medicamento) -> Unit, onEdit: (Medicamento) -> Unit) {

        binding.tvNomeMedicamento.text = itemView.context.getString(R.string.medication_name, medicamento.nome)

        //binding.tvNomeMedicamento.text = "Nome do remédio: ${medicamento.nome}"
        binding.tvDosagemMedicamento.text = "Dosagem: ${medicamento.dosagem}"
        binding.tvIntervaloMedicamento.text = "Intervalo entre doses: ${medicamento.intervaloDoses}"
        binding.tvHorariosMedicamento.text = "Horários: ${medicamento.horarios.joinToString(", ")}"

        binding.btnDeletar.setOnClickListener {
            onDelete(medicamento)
        }

        binding.btnEditar.setOnClickListener {
            onEdit(medicamento)
        }
    }
}

