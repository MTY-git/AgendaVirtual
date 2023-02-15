package com.oscarsainz.agenda.ui.asignaturas

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.data.Asignatura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AsignaturaViewModel(): ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state
    private val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()


    init {

        _state.value = _state.value?.copy( asignaturas = DbFirestore.getFlow(emailUser))

    }


    fun navigateTo(asignatura: Asignatura) {
        _state.value = _state.value?.copy(navigateTo = asignatura)
    }

    fun onNavigateDone(){
        _state.value = _state.value?.copy(navigateTo = null)
    }

    fun añadirAsignatura( emailUser:String , asignatura : Asignatura){
        viewModelScope.launch(Dispatchers.IO) {
            DbFirestore.añadirAsignatura(emailUser, asignatura )
        }
    }

    fun borrarAsignatura( emailUser:String , asignatura : Asignatura){
        viewModelScope.launch(Dispatchers.IO) {
            DbFirestore.borrarAsignatura(emailUser, asignatura )
        }
    }



    data class UiState(

        val asignaturas: Flow<List<Asignatura>>? = null,
        val navigateTo: Asignatura? = null,
    )

}