package com.oscarsainz.agenda.model.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.oscarsainz.agenda.databinding.DialogTareaBinding
import com.oscarsainz.agenda.model.data.Tarea
import java.util.*


class TareaDialog(
    private val onSubmitClickListener: (Tarea) -> Unit,

) : DialogFragment() {

    private lateinit var binding: DialogTareaBinding

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogTareaBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.botonAgregarDialogo.setOnClickListener {

            binding.apply {
                if (inputNombreDialogo.text!!.isNotBlank() and inputDescripcionDialogo.text!!.isNotBlank()) {
                    onSubmitClickListener.invoke(
                        Tarea(
                            inputNombreDialogo.text.toString(),
                            inputDescripcionDialogo.text.toString(),
                            Date(inputFechaDialogo.year-1900,inputFechaDialogo.month,inputFechaDialogo.dayOfMonth)
                        )
                    )
                    dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Debes introducir todos los datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

}