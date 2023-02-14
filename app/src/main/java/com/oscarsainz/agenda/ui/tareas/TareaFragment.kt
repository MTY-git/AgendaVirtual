package com.oscarsainz.agenda.ui.tareas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.databinding.FragTareasBinding

class TareaFragment : Fragment(R.layout.frag_tareas) {

    companion object {
        const val EXTRA_ASIGNATURA = "TareaFragment:Asignatura"
    }

    private lateinit var binding: FragTareasBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragTareasBinding.bind(view)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Tareas"


    }
}