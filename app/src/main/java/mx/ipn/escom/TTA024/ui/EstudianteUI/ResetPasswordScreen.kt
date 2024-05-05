package mx.ipn.escom.TTA024.ui.EstudianteUI

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import mx.ipn.escom.TTA024.ui.LoginScreens

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    email: String,
    modifier: Modifier = Modifier,
) {
    var code by remember { mutableStateOf("") }
    var pswd by remember { mutableStateOf("") }
    var pswdConfirm by remember { mutableStateOf("") }
    var pswdVisible by remember { mutableStateOf(false) }
    var pswdConfirmVisible by remember { mutableStateOf(false) }

    var loadConfirmResetPassword by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val validCode = remember(code){
        code.isEmpty() || Regex("^\\d{6}\$").matches(code)
    }
    val validPassword = remember(pswd){
        pswd.isEmpty() || Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,}\$").matches(pswd)
    }
    val validConfirmationPassword = remember(pswd, pswdConfirm){
        pswdConfirm.isEmpty() || pswd == pswdConfirm
    }
    val validForm = remember(code, pswd, pswdConfirm, validCode, validPassword, validConfirmationPassword){
        code.isNotEmpty() && pswd.isNotEmpty() && pswdConfirm.isNotEmpty() && validCode && validPassword && validConfirmationPassword
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
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Crea tu nueva contraseña",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ingresa el codigo que te enviamos a tu email para crear una nueva contraseña",
                    fontWeight = FontWeight.Normal
                )

                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { if(validCode) Text("Código") else Text("Solo 6 digitos") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validCode
                )

                OutlinedTextField(
                    value = pswd,
                    onValueChange = { pswd = it },
                    label = { if(validPassword) Text("Nueva contraseña") else Text("Debe contener al menos: 8 caracteres, mayúsculas, minúsculas y números.") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = if (pswdVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (pswdVisible)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility

                        // Please provide localized description for accessibility services
                        val description = if (pswdVisible) "Hide password" else "Show password"

                        IconButton(onClick = {pswdVisible = !pswdVisible}){
                            Icon(imageVector  = image, description)
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validPassword
                )

                OutlinedTextField(
                    value = pswdConfirm,
                    onValueChange = { pswdConfirm = it },
                    label = { if(validConfirmationPassword) Text("Confirmar Contraseña") else Text("Las contraseñas deben ser iguales") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (pswdConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (pswdConfirmVisible)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility
                        // Please provide localized description for accessibility services
                        val description = if (pswdConfirmVisible) "Hide password" else "Show password"

                        IconButton(onClick = {pswdConfirmVisible = !pswdConfirmVisible}){
                            Icon(imageVector  = image, description)
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validConfirmationPassword
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        loadConfirmResetPassword = true
                    },
                    modifier = modifier
                        .widthIn(min = 250.dp)
                        .padding(vertical = 8.dp),
                    enabled = validForm
                ) {
                    Text("Actualizar contraseña")
                }
                Spacer(modifier = Modifier.height(24.dp))

            }
        }
    }
    if(loadConfirmResetPassword){
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = true) {
            val resetPassswordResult = confirmResetPassword(email, pswd, code)
            if(resetPassswordResult){
                Toast.makeText(context, "Correcto, inicia sesion", Toast.LENGTH_SHORT).show()
                navController.popBackStack(LoginScreens.SignIn.name, true)
            }else{
                Toast.makeText(context, "Error, codigo incorrecto", Toast.LENGTH_SHORT).show()
            }

            loadConfirmResetPassword = false
        }
    }
}

suspend fun confirmResetPassword(email: String, password: String, code: String): Boolean{
    return try {
        Amplify.Auth.confirmResetPassword(email, password, code)
        Log.i("AuthQuickstart", "New password confirmed")
        true
    } catch (error: AuthException) {
        Log.e("AuthQuickstart", "Failed to confirm password reset", error)
        false
    }
}