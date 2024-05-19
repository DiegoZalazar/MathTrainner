package mx.ipn.escom.tta047.ui.estudianteUI.estudianteconfig

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.launch

sealed interface EstudianteConfigUIState {
    object Loading : EstudianteConfigUIState
    object Error : EstudianteConfigUIState
    data class Success(val nombre: String, val email: String) : EstudianteConfigUIState
}
class EstudianteConfigVM: ViewModel() {
    var estudianteConfigUIState: EstudianteConfigUIState by mutableStateOf(EstudianteConfigUIState.Loading)
        private set

    fun reload() {
        viewModelScope.launch {
            try {
                val attributes = Amplify.Auth.fetchUserAttributes()
                var name: String = ""
                var email: String = ""
                for(attribute in attributes){
                    if(attribute.key.keyString == "name"){
                        name = attribute.value
                    }
                    if(attribute.key.keyString == "email"){
                        email = attribute.value
                    }
                }
                estudianteConfigUIState = EstudianteConfigUIState.Success(nombre = name, email = email)
                Log.i("AuthDemo", "User attributes = $attributes")
            } catch (error: AuthException) {
                Log.e("AuthDemo", "Failed to fetch user attributes", error)
                estudianteConfigUIState = EstudianteConfigUIState.Error
            }
        }
    }
    init {
        reload()
    }
}