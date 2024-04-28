package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {
    private val auth = Amplify.Auth
    private val _loading = MutableLiveData(false)

    fun signIn(email: String, pswrd: String, home: () -> Unit) = viewModelScope.launch{
        try {
            var succed = false
            auth.signIn(email, pswrd,
                { result ->
                    if (result.isSignedIn) {
                        Log.i("AuthQuickstart", "Sign in succeeded")
                        GlobalScope.launch{
                            withContext(Dispatchers.Main) {
                                // Código que deseas ejecutar en el hilo principal
                                home()
                            }
                        }
                    } else {
                        Log.i("AuthQuickstart", "Sign in not complete")
                    }
                },
                {
                    Log.e("AuthQuickstart", "Failed to sign in", it)
                }
            )
            if(succed){home()}
        } catch (ex: Exception){
            Log.e("LoginViewModel", "Error: ${ex}")
        }

    }

}