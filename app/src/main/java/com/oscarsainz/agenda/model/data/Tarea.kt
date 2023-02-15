package com.oscarsainz.agenda.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tarea(

    var nombre: String = "",
    var descripcion: String = "",
    var fechaEntrega: String = ""

): Parcelable
