package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class MathTrainerViewModel() : ViewModel() {
    var isSignedIn by mutableStateOf(false)
    fun closeSesion() {
        Amplify.Auth.signOut {
            isSignedIn = false
        }
        Amplify.Auth.fetchAuthSession(
            {
                Log.i("AmplifyQuickstart", "Auth session = $it")
                isSignedIn = it.isSignedIn
            },
            { error -> Log.e("AmplifyQuickstart", "Failed to fetch auth session", error) }
        )
    }

    fun signUp(email: String, nombre: String, password: String){
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)  // no me roben mi cuenta
            .userAttribute(AuthUserAttributeKey.name(), nombre)
            .build()
        Amplify.Auth.signUp(email, password, options,
            { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
            { Log.e ("AuthQuickStart", "Sign up failed", it) }
        )
    }

    fun signIn(username: String, password: String) {
        Amplify.Auth.signIn(username, password,
                { result ->
                    if (result.isSignedIn) {
                        Log.i("AuthQuickstart", "Sign in succeeded")
                    } else {
                        Log.i("AuthQuickstart", "Sign in not complete")
                    }
                    isSignedIn = result.isSignedIn
                },
                { Log.e("AuthQuickstart", "Failed to sign in", it) }
        )
    }

    init {
        Amplify.Auth.fetchAuthSession(
            {
                Log.i("AmplifyQuickstart", "Auth session = $it")
                isSignedIn = it.isSignedIn
            },
            { error -> Log.e("AmplifyQuickstart", "Failed to fetch auth session", error) }
        )
    }
}