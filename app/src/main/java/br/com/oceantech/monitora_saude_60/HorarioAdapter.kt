package br.com.oceantech.monitora_saude_60

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class HorarioAdapter(

    private var horas: List<LocalTime>,
    private var nomes: List<String>
    ) : RecyclerView.Adapter<HorarioAdapter.HorarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horario, parent, false)
        return HorarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorarioViewHolder, position: Int) {

        val hora = horas[position]
        val nome = nomes[position]

        holder.bind(hora,nome)
    }

    override fun getItemCount(): Int {
        return nomes.size
    }

    // Função para atualizar os dados do adaptador
    fun updateData(horas: List<LocalTime>, nomes: List<String>) {
        this.horas = horas
        this.nomes = nomes
        notifyDataSetChanged()
    }

    inner class HorarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textHora: TextView = itemView.findViewById(R.id.textHora)
        private val textNomeRemedio: TextView = itemView.findViewById(R.id.textNomeRemedio)

        fun bind(hora: LocalTime, nome: String) {
            val formattedHora = hora.format(DateTimeFormatter.ofPattern("hh:mm a"))

            textHora.text = formattedHora
            textNomeRemedio.text = nome
        }
    }
}



