package com.oscarsainz.agenda.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asignatura(
    var name : String = ""
): Parcelable {
}