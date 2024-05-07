package mx.ipn.escom.TTA024.ui.viewmodels;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.usecases.GetModulosUseCase
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.usecases.DeleteModuloUseCase
import mx.ipn.escom.TTA024.domain.usecases.InsertModuloUseCase

class ModulosAdminViewModel: ViewModel() {

    private val getModulosUseCase: GetModulosUseCase = GetModulosUseCase()
    private val deleteModuloUseCase: DeleteModuloUseCase = DeleteModuloUseCase()
    private val insertModuloUseCase: InsertModuloUseCase = InsertModuloUseCase()

    val modulosModel = MutableLiveData<List<Modulo>>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getModulosUseCase()
            modulosModel.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun onDeleteModulo(modulo: Modulo){
        viewModelScope.launch {
            isLoading.postValue(true)
            deleteModuloUseCase(modulo.idModulo)
            modulosModel.value = modulosModel.value?.toMutableList()?.apply {
                remove(modulo)
            }?.toList()
            isLoading.postValue(false)
        }
    }

    fun onCreateModulo(modulo: Modulo){
        viewModelScope.launch {
            isLoading.postValue(true)
            insertModuloUseCase(modulo)
            modulosModel.value = modulosModel.value?.toMutableList()?.apply {
                add(modulo)
            }?.toList()
            isLoading.postValue(false)
        }
    }

}