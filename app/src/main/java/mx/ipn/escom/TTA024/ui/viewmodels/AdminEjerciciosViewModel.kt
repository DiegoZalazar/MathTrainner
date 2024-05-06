package mx.ipn.escom.TTA024.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.usecases.DeleteEjercicioByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.DeleteLeccionByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.GetEjerciciosByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.InsertEjercicioByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.InsertLeccionByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.UpdateEjercicioByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.UpdateLeccionByModuloUseCase
import javax.inject.Inject

@HiltViewModel
class AdminEjerciciosViewModel @Inject constructor(
    private val getEjerciciosByModuloUseCase: GetEjerciciosByModuloUseCase,
    private val deleteEjercicioByModuloUseCase: DeleteEjercicioByModuloUseCase,
    private val insertEjercicioByModuloUseCase: InsertEjercicioByModuloUseCase,
    private val updateEjercicioByModuloUseCase: UpdateEjercicioByModuloUseCase

) : ViewModel() {

    val leccionModel = MutableLiveData<List<Ejercicio>>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate(modulo: Modulo) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getEjerciciosByModuloUseCase(modulo.idModulo)
            leccionModel.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun onDeleteEjercicioByModulo(modulo: Modulo, ejercicio: Ejercicio){
        viewModelScope.launch {
            isLoading.postValue(true)
            deleteEjercicioByModuloUseCase(modulo.idModulo,ejercicio.idEjercicio)
            leccionModel.value = leccionModel.value?.toMutableList()?.apply {
                remove(ejercicio)
            }?.toList()
            isLoading.postValue(false)
        }
    }

    fun onCreateEjercicio(ejercicio: Ejercicio){
        viewModelScope.launch {
            isLoading.postValue(true)
            insertEjercicioByModuloUseCase(ejercicio)
            /*leccionModel.value = leccionModel.value?.toMutableList()?.apply {
                add(leccion)
            }?.toList()*/
            isLoading.postValue(false)
        }
    }

    fun onUpdateEjercicio(ejercicioAnterior: Ejercicio, ejercicio: Ejercicio){
        viewModelScope.launch {
            isLoading.postValue(true)
            updateEjercicioByModuloUseCase(ejercicio)
            /*val idAnterior=leccionModel.value?.toMutableList()?.indexOf(leccionAnterior)
            val updatedList = leccionModel.value?.toMutableList()
            updatedList!![idAnterior!!] = updatedList[idAnterior].copy(
                idLeccion = leccion.idLeccion,
                descripcionLeccion = leccion.descripcionLeccion,
                nivelLeccion = leccion.nivelLeccion,
                recursoMultimedia = leccion.recursoMultimedia,
                idModulo = leccion.idModulo,
                tituloLeccion = leccion.tituloLeccion
            )
            leccionModel.value=updatedList!!*/
        }
        isLoading.postValue(false)
    }


}