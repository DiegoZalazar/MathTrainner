package mx.ipn.escom.tta047.ui.adminUI

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.kotlin.core.Amplify
import mx.ipn.escom.tta047.R




@Composable
fun BodyPrincipalAdministrador(
    navController: NavController,
    onCloseSession: () -> Unit = {}
) {
    Column(modifier= Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Selecciona una opción:",
            fontStyle = FontStyle.Italic,
            fontSize = 24.sp,
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 30.dp, start = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.usuarioicon, "Consultar Usuarios", navController)
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.modulosicon, "Consultar Módulos", navController)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onCloseSession
        ) {
              Text("Cerrar sesion")
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalAdministrador(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        try {
            val attributes = Amplify.Auth.fetchUserAttributes()
            val name = attributes.find { it.key == AuthUserAttributeKey.name() }
            Log.i("AuthDemo", "User attributes = $attributes")
            Log.i("AuthDemo", name?.value?:"no se pudo obtener el nombre")
            userName = name?.value?:"no se pudo obtener el nombre"

            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            val token = session.tokensResult.value?.idToken?: "no hay token"
//            studentVM.updateToken(token)
//            studentVM.getModulos()
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to fetch user attributes", error)
        }
    }

    var closeSesionLoading by remember { mutableStateOf(false) }
    if(closeSesionLoading){
        LaunchedEffect(key1 = true) {
            try {
                Amplify.Auth.signOut()
            } catch (error: AuthException) {
                Log.e("AmplifyQuickstart", "Failed to sign out auth session", error)
            }
            navController.navigate("login"){
                popUpTo("admin") { inclusive = true }
            }
            closeSesionLoading = false
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Bienvenido, $userName")
            }
        )
    }
    ) {
        BodyPrincipalAdministrador(
            navController = navController,
            onCloseSession = { closeSesionLoading = true }
        )
    }

}
