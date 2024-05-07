package mx.ipn.escom.TTA024.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.usecases.DeleteEjercicioByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.GetEjerciciosByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.InsertEjercicioByModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.UpdateEjercicioByModuloUseCase

class AdminEjerciciosViewModel : ViewModel() {
    private val getEjerciciosByModuloUseCase: GetEjerciciosByModuloUseCase = GetEjerciciosByModuloUseCase()
    private val deleteEjercicioByModuloUseCase: DeleteEjercicioByModuloUseCase = DeleteEjercicioByModuloUseCase()
    private val insertEjercicioByModuloUseCase: InsertEjercicioByModuloUseCase = InsertEjercicioByModuloUseCase()
    private val updateEjercicioByModuloUseCase: UpdateEjercicioByModuloUseCase = UpdateEjercicioByModuloUseCase()

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