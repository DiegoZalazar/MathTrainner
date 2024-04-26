package mx.ipn.escom.TTA024

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import dagger.hilt.android.HiltAndroidApp
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions

@HiltAndroidApp
class principalAmplify: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Add these lines to add the `AWSApiPlugin` and `AWSCognitoAuthPlugin`

            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify.")
            // getUserList()
//            val options = AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "sergio_demian_ae@hotmail.com")  // no me roben mi cuenta
//                .userAttribute(AuthUserAttributeKey.name(), "demian")
//                .build()
//            Amplify.Auth.signUp("sergio_demian_ae@hotmail.com", "Password123", options,
//                { Log.i("AuthQuickStart", "Sign up succeeded: $it") },
//                { Log.e ("AuthQuickStart", "Sign up failed", it) }
//            )

//            Amplify.Auth.confirmSignUp(
//                "sergio_demian_ae@hotmail.com", "804037",
//                { result ->
//                    if (result.isSignUpComplete) {
//                        Log.i("AuthQuickstart", "Confirm signUp succeeded")
//                    } else {
//                        Log.i("AuthQuickstart","Confirm sign up not complete")
//                    }
//                },
//                { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
//            )


//            Amplify.Auth.signIn("sergio_demian_ae@hotmail.com", "Password123",
//                { result ->
//                    if (result.isSignedIn) {
//                        Log.i("AuthQuickstart", "Sign in succeeded")
//                    } else {
//                        Log.i("AuthQuickstart", "Sign in not complete")
//                    }
//                },
//                { Log.e("AuthQuickstart", "Failed to sign in", it) }
//            )

        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify.", error)
        }




    }
    fun getUserList() {
        val request = RestOptions.builder()
            .addPath("/listUsers")
            .build()

        Amplify.API.get("AdminQueries",request,
            { Log.i("MyAmplifyApp", "GET succeeded: $it") },
            { Log.e("MyAmplifyApp", "GET failed.", it) }
        )
    }
}