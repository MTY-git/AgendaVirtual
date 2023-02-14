package com.oscarsainz.agenda.model.bd

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.oscarsainz.agenda.model.Asignatura
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DbFirestore {

    private val db = FirebaseFirestore.getInstance()



    fun añadirUsuario(emailUser:String , provider: String) {
        db.collection("users").document(emailUser).set(
            hashMapOf("provider" to provider)
        )
    }


    fun añadirAsignatura(emailUser: String , asignatura: Asignatura) {
        db.collection("users/${emailUser}/asignaturas")
            .document(asignatura.nombre)
            .set(hashMapOf("nombre" to asignatura.nombre))
    }

    fun borrarAsignatura(emailUser: String, asignatura: Asignatura) {
        db.collection("users/${emailUser}/asignaturas")
            .document(asignatura.nombre).delete()
    }


    fun getFlow(emailUser: String): Flow<List<Asignatura>> {
        return db.collection("users/${emailUser}/asignaturas")
            .snapshots().map {
                it.toObjects(Asignatura::class.java)
            }
    }

}