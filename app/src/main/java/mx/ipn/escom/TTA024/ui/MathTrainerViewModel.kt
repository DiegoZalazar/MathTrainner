package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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

    fun initSesion() {
        Amplify.Auth.signIn("sergio_demian_ae@hotmail.com", "Password123",
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