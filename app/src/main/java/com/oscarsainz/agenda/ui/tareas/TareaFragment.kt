package com.oscarsainz.agenda.ui.tareas

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
import com.oscarsainz.agenda.databinding.FragTareasBinding
import com.oscarsainz.agenda.model.components.SwipeToDeleteCallback
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.components.TareaDialog
import com.oscarsainz.agenda.model.data.Tarea
import com.oscarsainz.agenda.ui.tareas.DetailTareaFragment.Companion.EXTRA_TAREA
import kotlinx.coroutines.launch

class TareaFragment : Fragment(R.layout.frag_tareas) {

    companion object {
        const val EXTRA_ASIGNATURA = "TareaFragment:Asignatura"
    }

    private  val viewModel : TareaViewModel by viewModels(){
        TareaViewModelFactory(arguments?.getParcelable<Asignatura>(EXTRA_ASIGNATURA)!!)
    }

    private lateinit var binding: FragTareasBinding
    private val adapter = TareasAdapter(){ tarea -> viewModel.navigateTo(tarea)}
    private val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragTareasBinding.bind(view)

        val asignatura = arguments?.getParcelable<Asignatura>(EXTRA_ASIGNATURA)!!
        (requireActivity() as AppCompatActivity).supportActionBar?.title = asignatura.nombre

        binding.recyclerView.adapter = adapter

        binding.floatingActionButton2.setOnClickListener {
            TareaDialog(
                onSubmitClickListener = {
                    viewModel.añadirTarea(emailUser , asignatura , it)
                }
            ).show(requireFragmentManager() , "Dialogo Tarea")
        }

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val nombre = adapter.tareas[position].nombre
                viewModel.borrarTarea( emailUser , asignatura , Tarea(nombre) )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        viewModel.state.observe(viewLifecycleOwner){state ->

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    state.tareas?.collect() {
                        adapter.tareas = it
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            state.navigateTo?.let {
                findNavController().navigate(
                    R.id.tarea_to_detail,
                    bundleOf(EXTRA_TAREA to it , DetailTareaFragment.EXTRA_ASIGNATURA to asignatura)

                )
                viewModel.onNavigateDone()
            }
        }


    }

}