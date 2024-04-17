package mx.ipn.escom.TTA024

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class principalAmplify: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Add these lines to add the `AWSApiPlugin` and `AWSCognitoAuthPlugin`

            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify.")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify.", error)
        }
    }
}