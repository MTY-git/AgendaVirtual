package com.oscarsainz.agenda.ui.tareas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.databinding.FragTareasDetailBinding
import com.oscarsainz.agenda.model.components.TareaDialog
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.data.Tarea

class DetailTareaFragment : Fragment(R.layout.frag_tareas_detail) {

    companion object {
        const val EXTRA_TAREA = "TareaFragment:Tarea"
        const val EXTRA_ASIGNATURA = "TareaFragment:Asignatura"
    }

    private  val viewModel : DetailViewModel by viewModels(){
        DetailViewModelFactory(arguments?.getParcelable<Tarea>(EXTRA_TAREA)!!)
    }

    private val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()

    private lateinit var binding: FragTareasDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragTareasDetailBinding.bind(view)

        val asignatura = arguments?.getParcelable<Asignatura>(EXTRA_ASIGNATURA)!!
        val tarea = arguments?.getParcelable<Tarea>(EXTRA_TAREA)!!

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Detalles"


        binding.switch1.setOnClickListener{
            tarea.completada = binding.switch1.isChecked
            viewModel.modificarTarea(emailUser , asignatura , tarea , tarea)
        }

        binding.floatingActionButton3.setOnClickListener {
            TareaDialog(
                onSubmitClickListener = {
                    viewModel.modificarTarea(emailUser , asignatura ,tarea, it)
                    Toast.makeText(requireContext(), getString(R.string.modificacionCompletada), Toast.LENGTH_SHORT).show()
                }
            ).show(requireFragmentManager() , "Dialogo Tarea")
        }


        viewModel.tarea.observe(viewLifecycleOwner){ tareaObserve ->

            bindingDetail(tareaObserve)

        }


    }

    @SuppressLint("SetTextI18n")
    private fun bindingDetail(tarea: Tarea) {

        binding.nombreTareaDetail.text = tarea.nombre
        binding.fechaEntrega.text = "${tarea.fechaEntrega?.date}/" +
                                    "${tarea.fechaEntrega?.month?.plus(1)}/" +
                                    "${tarea.fechaEntrega?.year?.plus(1900)}"
        binding.descripcionTarea.text = tarea.descripcion

        binding.switch1.isChecked = tarea.completada

    }
}