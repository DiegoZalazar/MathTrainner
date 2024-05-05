package mx.ipn.escom.TTA024.ui.EstudianteUI

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import mx.ipn.escom.TTA024.ui.LoginScreens
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ForgotPswdScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("") }
    var loadResetPassword by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val validEmail = remember(email){
        email.isEmpty() || Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matches(email)
    }

    if(loadResetPassword){
        Dialog(onDismissRequest = {}){
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = true) {
            val result = resetPassword(email)
            if(result){
                navController.navigate("${LoginScreens.ResetPassword.name}/${email}")
            }else{
                Toast.makeText(context, "Error, usuario no registrado", Toast.LENGTH_SHORT).show()
            }
            loadResetPassword = false
        }
    }

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
                    text = "Recuperar contraseña",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ingresa el email de tu cuenta para recuperar tu contraseña",
                    fontWeight = FontWeight.Normal
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { if(validEmail) Text("Email") else Text("Ingresa un email valido")},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validEmail
                )


                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { loadResetPassword = true },
                    modifier = modifier
                        .widthIn(min = 250.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Recuperar contraseña")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Regresar",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.clickable { navController.popBackStack(LoginScreens.SignIn.name, false) }
                )
            }
        }
    }
}

suspend fun resetPassword(email: String) : Boolean{
    return try {
        val result = Amplify.Auth.resetPassword(email)
        Log.i("AuthQuickstart", "Password reset OK: $result")
        true
    } catch (error: AuthException) {
        Log.e("AuthQuickstart", "Password reset failed", error)
        false
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun ForgotPswdScreenPreview(){
    MathTrainerTheme {
        ForgotPswdScreen(navController = rememberNavController())
    }
}