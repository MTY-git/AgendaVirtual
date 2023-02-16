package com.oscarsainz.agenda.ui.asignaturas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.databinding.FragAsignaturasBinding
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.components.AsignaturaDialog
import com.oscarsainz.agenda.model.components.SwipeToDeleteCallback
import com.oscarsainz.agenda.ui.tareas.TareaFragment
import kotlinx.coroutines.launch

class AsignaturaFragment : Fragment(R.layout.frag_asignaturas) {

    private val viewModel: AsignaturaViewModel by viewModels()
    private lateinit var binding: FragAsignaturasBinding
    private val adapter = AsignaturasAdapter(){ asig -> viewModel.navigateTo(asig)}
    private val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragAsignaturasBinding.bind(view)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.Asignaturas)

        binding.recyclerView.adapter = adapter


        binding.floatingActionButton.setOnClickListener {

            AsignaturaDialog(
                onSubmitClickListener = {
                    viewModel.añadirAsignatura( emailUser , it )
                }
            ).show(requireFragmentManager() , "Dialogo Asignatura")
        }


        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val nombre = adapter.asignaturas[position].nombre
                viewModel.borrarAsignatura( emailUser , Asignatura(nombre) )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


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
                        bundleOf(TareaFragment.EXTRA_ASIGNATURA to it)
                    )
                viewModel.onNavigateDone()
            }
        }



    }

}