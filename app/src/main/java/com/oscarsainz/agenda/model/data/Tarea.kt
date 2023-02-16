package com.oscarsainz.agenda.model.data

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Tarea(

    var nombre: String = "",
    var descripcion: String = "",
    @ServerTimestamp var fechaEntrega: Date? = null,
    var completada: Boolean = false

): Parcelable
