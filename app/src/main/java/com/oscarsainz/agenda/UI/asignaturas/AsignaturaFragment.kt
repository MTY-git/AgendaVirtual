package com.oscarsainz.agenda.UI.asignaturas

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
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.components.AsignaturaDialog
import com.oscarsainz.agenda.databinding.FragAsignaturasBinding
import com.oscarsainz.agenda.model.Asignatura
import kotlinx.coroutines.launch

class AsignaturaFragment : Fragment(R.layout.frag_asignaturas) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragAsignaturasBinding
    private val adapter = AsignaturasAdapter(){ asig -> viewModel.navigateTo(asig)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragAsignaturasBinding.bind(view).apply {
            recyclerView.adapter = adapter
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()

        binding.floatingActionButton.setOnClickListener {

            AsignaturaDialog(

                onSubmitClickListener = { nombre ->

                    if(nombre.isEmpty()){

                    }else{
                        DbFirestore.añadirAsignatura( emailUser , Asignatura(nombre) )
                    }
                }
            ).show(requireFragmentManager() , "Asignatura Dialog")
        }

        binding.recyclerView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner){state ->

            //GET || LIVEDATA

            /*state.asignaturas?.let {
                adapter.asignaturas = state.asignaturas
                adapter.notifyDataSetChanged()
            }*/
            //FLOW

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    state.asignaturas?.collect() {
                        adapter.asignaturas = DbFirestore.getAll(emailUser)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }


    }

}