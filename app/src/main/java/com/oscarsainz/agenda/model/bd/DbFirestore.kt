package com.oscarsainz.agenda.model.bd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.oscarsainz.agenda.model.Asignatura
import com.oscarsainz.agenda.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.A

object DbFirestore {

    private val db = FirebaseFirestore.getInstance()



    fun añadirUsuario(user: Usuario) {
        db.collection("users").document(user.email).set(
            hashMapOf("provider" to user.provider)
        )
    }


    fun añadirAsignatura(emailUser: String , asignatura: Asignatura) {

        db.collection("users/${emailUser}/asignaturas")
            .document(asignatura.nombre)
            .set(hashMapOf("nombre" to asignatura.nombre))
    }






    suspend fun getAll(emailUser: String): List<Asignatura> {
        val snapshot = FirebaseFirestore.getInstance()
            .collection("users/${emailUser}/asignaturas")
            .get()
            .await()
        val asignaturas = mutableListOf<Asignatura>()
        for (documentSnapshot in snapshot){
            val asignatura = documentSnapshot.toObject(Asignatura::class.java)
            asignaturas.add(asignatura)
        }
        return asignaturas
    }

    fun getFlow(emailUser: String): Flow<List<Asignatura>> {
        return FirebaseFirestore.getInstance()
            .collection("users/${emailUser}/asignaturas")
            .snapshots().map {
                it.toObjects(Asignatura::class.java)
            }
    }

    suspend fun getAllObservable(emailUser: String): LiveData<MutableList<Asignatura>> {

        return withContext(Dispatchers.IO) {
            val mutableData = MutableLiveData<MutableList<Asignatura>>()
            FirebaseFirestore.getInstance().collection("users/${emailUser}/asignaturas")
                .addSnapshotListener { snapshot, e ->
                    var listas = mutableListOf<Asignatura>()
                    if (snapshot != null) {
                        listas = snapshot.toObjects(Asignatura::class.java)
                    }
                    mutableData.value = listas
                }

            return@withContext mutableData
        }
    }


}