package mx.ipn.escom.TTA024.ui

//import android.renderscript.ScriptGroup.Input


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.SignedInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.ui.navigation.AppNavigation
import com.amplifyframework.ui.authenticator.ui.Authenticator
import mx.ipn.escom.TTA024.principalAmplify
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.ExampleNavExercises
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

// hollaaa
//import org.json.JSONObject
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MathTrainerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MathTrainer()
                }
            }
//            Authenticator { state ->   // no funcionan algunos componentes de este composable porque el daniel cambio las versiones de java y kotlin .I.
//                SignedInContent(state)
//            }
//            MathTrainerTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//
//                ) {
//                    MathTrainer()
//                }
//            }
        }
    }
}


@Composable
fun SignedInContent(state: SignedInState) {
    val scope = rememberCoroutineScope()

    Amplify.Auth.fetchAuthSession(
        {
            val session = it as AWSCognitoAuthSession
            when (session.identityIdResult.type) {
                AuthSessionResult.Type.SUCCESS ->
                    Log.i("AuthQuickStart", "IdentityId = ${session.userPoolTokensResult.value?.idToken.toString()}")
                AuthSessionResult.Type.FAILURE ->
                    Log.w("AuthQuickStart", "IdentityId not found")
            }
        },
        { Log.e("AuthQuickStart", "Failed to fetch session", it) }
    )

    Column {
        Text("You've signed in as ${state.user.username}!")
        Button(onClick = { scope.launch { state.signOut() } }) {
            Text("Sign Out")
        }
    }
}