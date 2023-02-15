package com.oscarsainz.agenda.model.bd

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.data.Tarea
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

    fun añadirTarea(emailUser: String , asignatura: Asignatura , tarea: Tarea) {
        db.collection("users/${emailUser}/asignaturas/${asignatura.nombre}/tareas")
            .document(tarea.nombre)
            .set(hashMapOf(
                "nombre" to tarea.nombre ,
                "descripcion" to tarea.descripcion ,
                "fechaEntrega" to tarea.fechaEntrega))
    }

    fun borrarTarea(emailUser: String, asignatura: Asignatura , tarea: Tarea) {
        db.collection("users/${emailUser}/asignaturas/${asignatura.nombre}/tareas")
            .document(tarea.nombre).delete()
    }

    fun getFlow(emailUser: String): Flow<List<Asignatura>> {
        return db.collection("users/${emailUser}/asignaturas")
            .snapshots().map {
                it.toObjects(Asignatura::class.java)
            }
    }

    fun getFlowTarea(emailUser: String , asignatura: Asignatura): Flow<List<Tarea>> {
        return db.collection("users/${emailUser}/asignaturas/${asignatura.nombre}/tareas")
            .snapshots().map {
                it.toObjects(Tarea::class.java)
            }
    }

}