package com.oscarsainz.agenda.model.data

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Tarea(

    var name: String = "",
    var description: String = "",
    var deadline: String = "",
    var completed: Boolean = false

): Parcelable
