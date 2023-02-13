package com.oscarsainz.agenda.ui.asignaturas

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.components.AsignaturaDialog
import com.oscarsainz.agenda.databinding.FragAsignaturasBinding
import com.oscarsainz.agenda.model.Asignatura
import com.oscarsainz.agenda.ui.tareas.TareaFragment
import kotlinx.coroutines.launch

class AsignaturaFragment : Fragment(R.layout.frag_asignaturas) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragAsignaturasBinding
    private val adapter = AsignaturasAdapter(){ asig -> viewModel.navigateTo(asig)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.Asignaturas)

        val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()

        binding = FragAsignaturasBinding.bind(view)
        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener {

            AsignaturaDialog(
                onSubmitClickListener = { nombre ->
                    if(nombre.isEmpty()){
                        viewModel.añadirAsignatura( emailUser , Asignatura(nombre) )
                    }
                }
            ).show(requireFragmentManager() , "Dialogo Asignatura")
        }

        viewModel.state.observe(viewLifecycleOwner){state ->

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    state.asignaturas?.collect() {
                        adapter.asignaturas = it
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            state.navigateTo?.let {
                findNavController().navigate(
                    R.id.asig_to_tarea,
                    //bundleOf(TareaFragment.EXTRA_ASIGNATURA to it)
                )
                viewModel.onNavigateDone()
            }
        }



    }

}