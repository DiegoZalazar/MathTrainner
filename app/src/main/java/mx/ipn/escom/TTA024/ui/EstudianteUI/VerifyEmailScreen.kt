package mx.ipn.escom.TTA024.ui.EstudianteUI

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify

import mx.ipn.escom.TTA024.ui.MathTrainerNavScreens
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun VerifyEmailScreen(
    navController: NavController,
    email: String,
    modifier: Modifier = Modifier,
) {
    var code by remember { mutableStateOf("") }

    val context = LocalContext.current

    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MathTrainer",
            style = TextStyle(
                fontSize = 32.sp,
                color = Color(0xFFD62839),
                fontWeight = FontWeight.Bold
            )
        )
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
                    text = "Confirma tu correo",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Te enviamos un código a tu correo. Ingresa el código",
                    fontWeight = FontWeight.Normal
                )
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Código") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text("¿No has recibido ningun código?")
                Text("Checa la carpeta de Spam o")
                Text(
                    text = "Reenvia el codigo",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        loading = true

                    },
                    modifier = modifier
                        .widthIn(min = 250.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Confirmar correo")
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
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = true) {
            if(confirmSignUp(email, code)){
                Toast.makeText(context, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show()
                navController.navigate("home"){
                    popUpTo("login")
                }
            }else{
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                navController.popBackStack(MathTrainerNavScreens.SignIn.name, true)
            }
            loading = false
        }

    }
}

suspend fun confirmSignUp(email: String, code: String): Boolean {
    return try {
        val result = Amplify.Auth.confirmSignUp(email, code)
        if (result.isSignUpComplete) {
            Log.i("AuthQuickstart", "Signup confirmed")
        } else {
            Log.i("AuthQuickstart", "Signup confirmation not yet complete")
        }
        result.isSignUpComplete
    } catch (error: AuthException) {
        Log.e("AuthQuickstart", "Failed to confirm signup", error)
        return false
    }
}

//var succeed = false
//Amplify.Auth.confirmSignUp(
//email, code,
//{ result ->
//    if (result.isSignUpComplete) {
//        Log.i("AuthQuickstart", "Confirm signUp succeeded")
//        succeed = true
//    } else {
//        Log.i("AuthQuickstart","Confirm sign up not complete")
//    }
//},
//{ Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
//)
//return succeed

//@Preview(showBackground = true, device = "id:pixel_5")
//@Composable
//fun VerifyEmailScreenPreview(){
//    MathTrainerTheme {
//        VerifyEmailScreen()
//    }
//}