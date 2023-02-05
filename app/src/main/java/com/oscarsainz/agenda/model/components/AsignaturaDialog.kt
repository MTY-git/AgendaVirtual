package com.oscarsainz.agenda.model.components

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.oscarsainz.agenda.databinding.DialogAsignaturaBinding


class AsignaturaDialog(
    private val onSubmitClickListener: (String) -> Unit

): DialogFragment() {

    private lateinit var binding : DialogAsignaturaBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAsignaturaBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.botonAgregarDialogo.setOnClickListener {
            onSubmitClickListener.invoke( binding.inputNombreDialogo.text.toString() )
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

}