package com.oscarsainz.agenda.UI.asignaturas

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.Asignatura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {

    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state
    private val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()



    init {

        //FLOW
        _state.value = _state.value?.copy( asignaturas = DbFirestore.getFlow(emailUser))

        /*
        //LIVEDATA
        viewModelScope.launch(Dispatchers.Main) {

            DbFirestore.getAllObservable(emailUser).observeForever {
                _state.value = _state.value?.copy(asignaturas = it)
            }

        }

        //GET
        viewModelScope.launch(Dispatchers.Main) {
            _state.value = _state.value?.copy( asignaturas = requestMovies())
        }
        */
    }

    private suspend fun requestMovies(): List<Asignatura>  = DbFirestore.getAll(emailUser)


    fun navigateTo(asignatura: Asignatura) {
        _state.value = _state.value?.copy(navigateTo = asignatura)
    }

    fun onNavigateDone(){
        _state.value = _state.value?.copy(navigateTo = null)
    }

    data class UiState(
        //val asignaturas: List<Asignatura>? = null,
        val asignaturas: Flow<List<Asignatura>>? = null,
        val navigateTo: Asignatura? = null,
    )

}