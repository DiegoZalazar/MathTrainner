package mx.ipn.escom.TTA024.ui

//import android.renderscript.ScriptGroup.Input


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.SignedInState
import com.amplifyframework.ui.authenticator.ui.Authenticator
import kotlinx.coroutines.launch

// hollaaa
//import org.json.JSONObject

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Authenticator { state ->
                SignedInContent(state)
            }

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