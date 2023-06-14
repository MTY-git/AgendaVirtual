package com.oscarsainz.agenda.ui.asignaturas


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.databinding.ElemAsignaturaBinding
import com.oscarsainz.agenda.model.data.Asignatura


class AsignaturasAdapter(val listener: (Asignatura) -> Unit) :

    RecyclerView.Adapter<AsignaturasAdapter.ViewHolder>() {

    var asignaturas = emptyList<Asignatura>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elem_asignatura, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val asignatura = asignaturas[position]
        holder.bind(asignatura)

        holder.itemView.setOnClickListener {
            listener(asignatura)
        }

        
    }

    override fun getItemCount(): Int = asignaturas.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = ElemAsignaturaBinding.bind(view)

        fun bind(asignatura: Asignatura){
            binding.nombreTarea.text = asignatura.name
        }
    }
}