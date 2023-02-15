package com.oscarsainz.agenda.ui.tareas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.databinding.FragTareasDetailBinding

class DetailTareaFragment : Fragment(R.layout.frag_tareas_detail) {

    companion object {
        const val EXTRA_TAREA = "TareaFragment:Tarea"
    }

    private lateinit var binding: FragTareasDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragTareasDetailBinding.bind(view)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "DETALLE"



    }
}