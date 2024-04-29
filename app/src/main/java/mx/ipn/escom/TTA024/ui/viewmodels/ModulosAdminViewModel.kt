package mx.ipn.escom.TTA024.ui.viewmodels;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.usecases.GetModulosUseCase
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.usecases.DeleteModuloUseCase
import javax.inject.Inject

@HiltViewModel
class ModulosAdminViewModel @Inject constructor(
    private val getModulosUseCase: GetModulosUseCase,
    private val deleteModuloUseCase: DeleteModuloUseCase,
) : ViewModel() {

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

}