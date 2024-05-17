package mx.ipn.escom.tta047.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.tta047.domain.model.Leccion
import mx.ipn.escom.tta047.domain.model.Modulo
import mx.ipn.escom.tta047.domain.usecases.DeleteLeccionByModuloUseCase
import mx.ipn.escom.tta047.domain.usecases.GetLeccionesByModuloUseCase
import mx.ipn.escom.tta047.domain.usecases.InsertLeccionByModuloUseCase
import mx.ipn.escom.tta047.domain.usecases.UpdateLeccionByModuloUseCase

class AdminLeccionesViewModel: ViewModel() {
    private val getLeccionesByModuloUseCase: GetLeccionesByModuloUseCase = GetLeccionesByModuloUseCase()
    private val deleteLeccionByModuloUseCase: DeleteLeccionByModuloUseCase = DeleteLeccionByModuloUseCase()
    private val insertLeccionByModuloUseCase: InsertLeccionByModuloUseCase = InsertLeccionByModuloUseCase()
    private val updateLeccionByModuloUseCase: UpdateLeccionByModuloUseCase = UpdateLeccionByModuloUseCase()
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