package mx.ipn.escom.tta047.ui.viewmodels;

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.tta047.domain.usecases.GetModulosUseCase
import mx.ipn.escom.tta047.domain.model.Modulo
import mx.ipn.escom.tta047.domain.usecases.DeleteModuloUseCase
import mx.ipn.escom.tta047.domain.usecases.InsertModuloUseCase

class ModulosAdminViewModel: ViewModel() {

    private val getModulosUseCase: GetModulosUseCase = GetModulosUseCase()
    private val deleteModuloUseCase: DeleteModuloUseCase = DeleteModuloUseCase()
    private val insertModuloUseCase: InsertModuloUseCase = InsertModuloUseCase()

    val modulosModel = MutableLiveData<List<Modulo>>()
    val isLoading = MutableLiveData<Boolean>()


    fun onGetModulos() {
        viewModelScope.launch {
            isLoading.postValue(true)
            Log.i("ModulosViewModel", "Getting modulos VM")
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
            onGetModulos()
            isLoading.postValue(false)
        }
    }

}