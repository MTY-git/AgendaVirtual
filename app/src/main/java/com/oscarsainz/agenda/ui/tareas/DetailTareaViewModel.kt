package com.oscarsainz.agenda.ui.tareas

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.data.Tarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel(tarea: Tarea): ViewModel() {


    fun modificarTarea(emailUser: String, asignatura: Asignatura, tareaAntigua: Tarea, tarea: Tarea) {
        viewModelScope.launch(Dispatchers.IO) {
            DbFirestore.modificarTarea(emailUser,asignatura,tareaAntigua,tarea)
        }
    }

    private val _tarea = MutableLiveData(tarea)
    val tarea: LiveData<Tarea> get() = _tarea
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val tarea: Tarea): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(tarea) as T
    }

}