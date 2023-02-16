package com.oscarsainz.agenda.ui.tareas

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.model.data.Asignatura
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.data.Tarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class TareaViewModel(asignatura: Asignatura): ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state
    private val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()


    init {

        _state.value = _state.value?.copy( tareas = DbFirestore.getFlowTarea( emailUser, asignatura ))

    }


    fun navigateTo(tarea: Tarea) {
        _state.value = _state.value?.copy(navigateTo = tarea)
    }

    fun onNavigateDone(){
        _state.value = _state.value?.copy(navigateTo = null)
    }

    fun añadirTarea( emailUser:String , asignatura : Asignatura , tarea: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            DbFirestore.añadirTarea(emailUser, asignatura , tarea )
        }
    }

    fun borrarTarea( emailUser:String , asignatura : Asignatura, tarea: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            DbFirestore.borrarTarea(emailUser, asignatura , tarea )
        }
    }


    data class UiState(

        val tareas: Flow<List<Tarea>>? = null,
        val navigateTo: Tarea? = null,
    )

}

@Suppress("UNCHECKED_CAST")
class TareaViewModelFactory(private val asignatura: Asignatura): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TareaViewModel(asignatura) as T
    }

}

