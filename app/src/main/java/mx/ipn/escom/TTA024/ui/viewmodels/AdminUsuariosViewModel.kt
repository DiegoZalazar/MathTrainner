package mx.ipn.escom.TTA024.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.model.Usuario
import mx.ipn.escom.TTA024.domain.usecases.DeleteUsuarioUseCase
import mx.ipn.escom.TTA024.domain.usecases.GetUsuariosUseCase
import mx.ipn.escom.TTA024.domain.usecases.UpdateUsuarioUseCase

class AdminUsuariosViewModel: ViewModel() {
    private val getUsuariosUseCase: GetUsuariosUseCase = GetUsuariosUseCase()
    private val deleteUsuariosUseCase: DeleteUsuarioUseCase = DeleteUsuarioUseCase()
    private val updateUsuarioUseCase: UpdateUsuarioUseCase = UpdateUsuarioUseCase()

    val usuarioModel = MutableLiveData<List<Usuario>>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUsuariosUseCase()
            usuarioModel.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun onDeleteUsuario(usuario: Usuario){
        viewModelScope.launch {
            isLoading.postValue(true)
            deleteUsuariosUseCase(usuario.user_id)
            usuarioModel.value = usuarioModel.value?.toMutableList()?.apply {
                remove(usuario)
            }?.toList()
            isLoading.postValue(false)
        }
    }

    fun onUpdateUsuario(usuario: Usuario){
        viewModelScope.launch {
            isLoading.postValue(true)
            updateUsuarioUseCase(usuario)
            isLoading.postValue(false)
        }
    }



}