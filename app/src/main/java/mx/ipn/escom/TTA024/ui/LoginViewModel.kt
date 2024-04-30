package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.delay
import mx.ipn.escom.TTA024.ui.EstudianteUI.AlertSignInState

class LoginViewModel() : ViewModel() {
    var loading by mutableStateOf(false)

    suspend fun signIn(email: String, pswrd: String, home: () -> Unit) : AlertSignInState {
        loading = true
        var title = ""
        var msg = ""
        var success = false
        var show = true
        delay(2000L)
        try {
            val result = Amplify.Auth.signIn(email, pswrd)
            if (result.isSignedIn) {
                Log.i("AuthQuickstart", "Sign in succeeded")
                success = true
                show = false
                home()
            } else {
                Log.e("AuthQuickstart", "Sign in not complete")
                title = "Error"
                msg = "Inicio de sesion no completado"
            }
        } catch (error: AuthException) {
            Log.e("AuthQuickstart", "Sign in failed", error)
            title = "Error"
            msg = "Contraseña o Email incorrecto"
        }
        loading = false
        return AlertSignInState(title= title, msg = msg, success = success, show = show)
    }

}
