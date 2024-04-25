package mx.ipn.escom.TTA024.ui.viewmodels;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.usecases.GetModulosUseCase
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

@HiltViewModel
class ModuloViewModel @Inject constructor(
    private val getModulosUseCase: GetModulosUseCase,
) : ViewModel() {

    val modulosModel = MutableLiveData<List<Modulo>>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getModulosUseCase()
            modulosModel.postValue(result)
        }
    }

}