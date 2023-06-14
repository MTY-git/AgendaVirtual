package com.oscarsainz.agenda.model.bd

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.data.Tarea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

object DbFirestore {

    private val db = FirebaseFirestore.getInstance()



    fun añadirUsuario(emailUser:String , provider: String) {
        db.collection("users").document(emailUser).set(
            hashMapOf("provider" to provider)
        )
    }


    fun añadirAsignatura(emailUser: String , asignatura: Asignatura) {
        db.collection("users/${emailUser}/subjects")
            .add(asignatura)
    }

    fun borrarAsignatura(emailUser: String, asignatura: Asignatura) {
        db.collection("users/${emailUser}/subjects")
            .whereEqualTo("name" , asignatura.name)
            .get()
            .addOnCompleteListener {
                val id = it.result.first().id
                db.collection("users/${emailUser}/subjects")
                    .document(id)
                    .delete()
            }
    }

    fun añadirTarea(emailUser: String , asignatura: Asignatura , tarea: Tarea) {

        db.collection("users/${emailUser}/subjects")
            .whereEqualTo("name" , asignatura.name)
            .get()
            .addOnCompleteListener {
                val id = it.result.first().id
                db.collection("users/${emailUser}/subjects/${id}/tasks")
                    .add(tarea)
            }

    }

    fun borrarTarea(emailUser: String, asignatura: Asignatura , tarea: Tarea) {

        db.collection("users/${emailUser}/subjects")
            .whereEqualTo("name" , asignatura.name)
            .get()
            .addOnCompleteListener { it ->
                val idSubject = it.result.first().id
                db.collection("users/${emailUser}/subjects/${idSubject}/tasks")
                    .whereEqualTo("name" , tarea.name)
                    .get()
                    .addOnCompleteListener { it ->
                        val idTask = it.result.first().id
                        db.collection("users/${emailUser}/subjects/${idSubject}/tasks")
                            .document(idTask)
                            .delete()
                    }
            }

    }

    fun modificarTarea(emailUser: String, asignatura: Asignatura , tareaAntigua: Tarea , tarea: Tarea) {

        db.collection("users/${emailUser}/subjects")
            .whereEqualTo("name" , asignatura.name)
            .get()
            .addOnCompleteListener {
                val idSubject = it.result.first().id
                db.collection("users/${emailUser}/subjects/${idSubject}/tasks")
                    .whereEqualTo("name" , tareaAntigua.name)
                    .get()
                    .addOnCompleteListener {
                        val idTask = it.result.first().id
                        db.collection("users/${emailUser}/subjects/${idSubject}/tasks")
                            .document(idTask)
                            .update("name" , tarea.name
                                , "description" , tarea.description
                                , "deadline", tarea.deadline
                                , "completed",tarea.completed)
                    }
            }

    }




    fun getFlow(emailUser: String): Flow<List<Asignatura>> {
        return db.collection("users/${emailUser}/subjects")
            .snapshots().map {
                it.toObjects(Asignatura::class.java)
            }
    }

    fun getFlowTarea(emailUser: String, asignatura: Asignatura): Flow<List<Tarea>> {
        val id = runBlocking { getCurrentSubject(emailUser, asignatura) }
        val tareas = db.collection("users/$emailUser/subjects/${id}/tasks")
            .snapshots().map { snapshot ->
                snapshot.toObjects(Tarea::class.java)
            }
        return tareas
    }

    suspend fun getCurrentSubject(emailUser: String, asignatura: Asignatura): String {
        var idSubject = ""
        val querySnapshot = db.collection("users/$emailUser/subjects")
            .whereEqualTo("name", asignatura.name)
            .get()
            .await()
        if (!querySnapshot.isEmpty) {
            val documentSnapshot = querySnapshot.documents.first()
            idSubject = documentSnapshot.id
        }
        return idSubject
    }



}