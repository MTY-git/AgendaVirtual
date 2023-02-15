package com.oscarsainz.agenda.model.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.oscarsainz.agenda.databinding.DialogAsignaturaBinding
import com.oscarsainz.agenda.model.data.Asignatura


class AsignaturaDialog(
    private val onSubmitClickListener: (Asignatura) -> Unit

): DialogFragment() {

    private lateinit var binding : DialogAsignaturaBinding

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAsignaturaBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.botonAgregarDialogo.setOnClickListener {
            if(binding.inputNombreDialogo.text!!.isNotBlank()){
                onSubmitClickListener.invoke( Asignatura(binding.inputNombreDialogo.text.toString()))
                dismiss()
            }else{
                Toast.makeText(
                    requireContext(),
                    "Debes introducir el nombre de la asignatura",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

}