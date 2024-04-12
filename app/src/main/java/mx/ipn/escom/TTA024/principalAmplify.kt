package mx.ipn.escom.TTA024

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class principalAmplify: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Add these lines to add the `AWSApiPlugin` and `AWSCognitoAuthPlugin`

            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify.")
            getUserList()
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify.", error)
        }




    }
    public fun getUserList() {
        val request = RestOptions.builder()
            .addPath("/listUsers")
            .build()

        Amplify.API.get("AdminQueries",request,
            { Log.i("MyAmplifyApp", "GET succeeded: $it") },
            { Log.e("MyAmplifyApp", "GET failed.", it) }
        )
    }
}