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
import com.oscarsainz.agenda.databinding.DialogTareaBinding
import com.oscarsainz.agenda.model.data.Tarea
import java.util.*


class TareaDialog(
    private val modo: String,
    private val tarea: Tarea,
    private val onSubmitClickListener: (Tarea) -> Unit

) : DialogFragment() {

    private lateinit var binding: DialogTareaBinding

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogTareaBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        if (modo.equals("modificacion")) {

            binding.botonAgregarDialogo.text = "Modificar"
            binding.inputNombreDialogo.setText(tarea.name)
            binding.inputDescripcionDialogo.setText(tarea.description)

        }

        binding.botonAgregarDialogo.setOnClickListener {

            binding.apply {
                if (inputNombreDialogo.text!!.isNotBlank() and inputDescripcionDialogo.text!!.isNotBlank()) {
                    onSubmitClickListener.invoke(
                        Tarea(
                            inputNombreDialogo.text.toString(),
                            inputDescripcionDialogo.text.toString(),
                            tarea.deadline,
                            tarea.completed
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