package br.com.oceantech.monitora_saude_60.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.oceantech.monitora_saude_60.ListaMedicamentoActivity
import br.com.oceantech.monitora_saude_60.databinding.ItemMedicamentoBinding
import br.com.oceantech.monitora_saude_60.model.Medicamento

class MedicamentoListAdapter(
    private val activity: ListaMedicamentoActivity,
    private val onDelete: (Medicamento) -> Unit,
    private val onEdit: (Medicamento) -> Unit
) : RecyclerView.Adapter<MedicamentoViewHolder>() {
    private var medicamentos: List<Medicamento> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentoViewHolder {
        val binding = ItemMedicamentoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicamentoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicamentoViewHolder, position: Int) {
        holder.bind(medicamentos[position], onDelete, onEdit)
    }

    override fun getItemCount(): Int {
        return medicamentos.size
    }

    fun setData(data: List<Medicamento>) {
        medicamentos = data
        notifyDataSetChanged()
    }

    fun updateList(medicamentos: List<Medicamento>?) {
        medicamentos?.let {
            setData(it)
            notifyDataSetChanged()
        }
    }

}

