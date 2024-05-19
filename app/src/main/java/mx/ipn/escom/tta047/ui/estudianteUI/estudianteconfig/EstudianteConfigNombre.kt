package mx.ipn.escom.tta047.ui.estudianteUI.estudianteconfig

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.kotlin.core.Amplify

@Composable
fun EstudianteConfigNombre(
    navController: NavController
){
    var newNombre by remember { mutableStateOf("") }
    val validName = remember(newNombre){
        newNombre.isEmpty() || Regex("^[a-zA-Z0-9_ -]{3,60}\$").matches(newNombre)
    }

    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .width(330.dp)
                .border(
                    width = 1.dp, // Border width
                    color = Color.LightGray, // Border color
                    shape = MaterialTheme.shapes.large // Border shape (optional)
                )
                .padding(16.dp)
                .wrapContentHeight()
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Cambiar nombre",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ingresa tu nuevo nombre",
                    fontWeight = FontWeight.Normal
                )
                OutlinedTextField(
                    value = newNombre,
                    onValueChange = { newNombre = it },
                    label = { if(validName) Text("Nombre") else Text("Al menos 3 caracteres") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validName
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        loading = true
                    },
                    modifier = Modifier
                        .widthIn(min = 250.dp)
                        .padding(vertical = 8.dp),
                    enabled = newNombre.isNotEmpty() && validName
                ) {
                    Text("Cambiar nombre")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Regresar",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }

    if(loading){
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = true) {
            if(updateNombre(newNombre)){
                Toast.makeText(context, "Correcto, se ha cambiado el nombre", Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }else{
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
            }
            loading = false
        }
    }
}

suspend fun updateNombre(nombre: String): Boolean{
    val nombreAttribute = AuthUserAttribute(AuthUserAttributeKey.name(), nombre)
    try {
        val result = Amplify.Auth.updateUserAttribute(nombreAttribute)
        Log.i("AuthDemo", "Updated user attribute = $result")
        return true
    } catch (error: AuthException) {
        Log.e("AuthDemo", "Failed to update user attribute.", error)
        return false
    }
}