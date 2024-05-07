package mx.ipn.escom.TTA024.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.usecases.DeleteLeccionByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.DeleteModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.GetLeccionesByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.GetModulosUseCase
import mx.ipn.escom.TTA024.domain.usecases.InsertLeccionByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.InsertModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.UpdateLeccionByModuloUseCase
import javax.inject.Inject

@HiltViewModel
class AdminLeccionesViewModel @Inject constructor(
    private val getLeccionesByModuloUseCase: GetLeccionesByModuloUseCase,
    private val deleteLeccionByModuloUseCase: DeleteLeccionByModuloUseCase,
    private val insertLeccionByModuloUseCase: InsertLeccionByModuloUseCase,
    private val updateLeccionByModuloUseCase: UpdateLeccionByModuloUseCase

) : ViewModel() {

    val leccionModel = MutableLiveData<List<Leccion>>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate(modulo: Modulo) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getLeccionesByModuloUseCase(modulo.idModulo)
            leccionModel.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun onDeleteLeccionByModulo(modulo: Modulo, leccion: Leccion){
        viewModelScope.launch {
            isLoading.postValue(true)
            deleteLeccionByModuloUseCase(modulo.idModulo,leccion.idLeccion)
            leccionModel.value = leccionModel.value?.toMutableList()?.apply {
                remove(leccion)
            }?.toList()
            isLoading.postValue(false)
        }
    }

    fun onCreateLeccion(leccion: Leccion){
        viewModelScope.launch {
            isLoading.postValue(true)
            Log.i("leccion vm",leccion.idModulo.toString())
            insertLeccionByModuloUseCase(leccion)
            /*leccionModel.value = leccionModel.value?.toMutableList()?.apply {
                add(leccion)
            }?.toList()*/
            isLoading.postValue(false)
        }
    }

    fun onUpdateLeccion(leccionAnterior: Leccion,leccion: Leccion){
        viewModelScope.launch {
            isLoading.postValue(true)
            updateLeccionByModuloUseCase(leccion)
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
    }


}