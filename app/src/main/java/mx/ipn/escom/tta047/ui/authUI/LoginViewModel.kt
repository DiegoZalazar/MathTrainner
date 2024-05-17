package mx.ipn.escom.tta047.ui.authUI

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.delay
import mx.ipn.escom.tta047.ui.smallcomponents.SignInAlertState

class LoginViewModel() : ViewModel() {
    var loading by mutableStateOf(false)

    suspend fun signIn(email: String, pswrd: String, home: () -> Unit) : SignInAlertState {
        loading = true
        var title = ""
        var msg = ""
        var error = false
        var show = true
        delay(2000L)
        try {
            val result = Amplify.Auth.signIn(email, pswrd)
            if (result.isSignedIn) {
                Log.i("AuthQuickstart", "Sign in succeeded")
                error = false
                show = false
                home()
            } else {
                Log.e("AuthQuickstart", "Sign in not complete")
                title = "Error"
                msg = "Inicio de sesion no completado"
                error = true
            }
        } catch (_error: AuthException) {
            Log.e("AuthQuickstart", "Sign in failed", _error)
            title = "Error"
            msg = "Contraseña o email incorrecto"
            error = true
        }
        loading = false
        return SignInAlertState(title= title, msg = msg, error = error, show = show)
    }

}
