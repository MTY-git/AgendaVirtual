package com.oscarsainz.agenda.ui.tareas


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.databinding.ElemTareaBinding
import com.oscarsainz.agenda.model.data.Tarea


class TareasAdapter(val listener: (Tarea) -> Unit) :

    RecyclerView.Adapter<TareasAdapter.ViewHolder>() {

    var tareas = emptyList<Tarea>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elem_tarea, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tarea = tareas[position]
        holder.bind(tarea)

        holder.itemView.setOnClickListener {
            listener(tarea)
        }

        
    }

    override fun getItemCount(): Int = tareas.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = ElemTareaBinding.bind(view)

        fun bind(tarea : Tarea){
            binding.nombreTarea.text = tarea.nombre
            binding.fecha.text = tarea.fechaEntrega.toString()
        }
    }
}