package mx.ipn.escom.tta047.ui.authUI

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify

import mx.ipn.escom.tta047.ui.LoginScreens

@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pswd by remember { mutableStateOf("") }
    var pswdConfirm by remember { mutableStateOf("") }
    var pswdVisible by remember { mutableStateOf(false) }
    var pswdConfirmVisible by remember { mutableStateOf(false) }
    var terms by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var isSignUpComplete = false
    val validName = remember(name){
        name.isEmpty() || Regex("^[a-zA-Z0-9_ -]{3,60}\$").matches(name)
    }
    val validEmail = remember(email){
        email.isEmpty() || Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+").matches(email)
    }
    val validPassword = remember(pswd){
        pswd.isEmpty() || Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,}\$").matches(pswd)
    }
    val validConfirmationPassword = remember(pswd, pswdConfirm){
        pswdConfirm.isEmpty() || pswd == pswdConfirm
    }
    val validForm = remember(name, email, pswd, pswdConfirm, validName, validEmail, validPassword, validConfirmationPassword){
        name.isNotEmpty() && email.isNotEmpty() && pswd.isNotEmpty() && pswdConfirm.isNotEmpty() && validName && validEmail && validPassword && validConfirmationPassword
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
                    text = "Registrate",
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { if(validName) Text("Nombre") else Text("Al menos 3 caracteres") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validName
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { if(validEmail) Text("Email") else Text("Ingresa un email valido")} ,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    isError = !validEmail
                )

                OutlinedTextField(
                    value = pswd,
                    onValueChange = { pswd = it },
                    label = { if(validPassword) Text("Contraseña") else Text("Debe contener al menos: 8 caracteres, mayúsculas, minúsculas y números.") },
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

//                Row(
//                    modifier = modifier
//                        .wrapContentHeight()
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Checkbox(
//                        checked = terms,
//                        onCheckedChange = { terms = it },
//                    )
//                    Text(
//                        text = "Acepto terminos y condiciones",
//                        style = MaterialTheme.typography.bodySmall,
//                        fontWeight = FontWeight.Bold
//                    )
//                }

                Button(
                    onClick = {
                        loading = true

                    },
                    modifier = modifier
                        .widthIn(min = 250.dp)
                        .padding(vertical = 8.dp),
                    enabled = validForm
                ) {
                    Text("Crear cuenta")
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("¿Ya tienes una cuenta?")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Inicia sesion",
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.clickable( onClick = {
                        navController.popBackStack(LoginScreens.SignIn.name, false)
                    } )
                )
            }
        }
    }
    if(loading){
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = true) {
            isSignUpComplete = signUp(email = email, nombre = name, password = pswdConfirm)
            if(isSignUpComplete){
                navController.navigate("home"){
                    popUpTo("login")
                }
            }else{
                navController.navigate("${LoginScreens.VerifyCode.name}/${email}")
            }
            loading = false
        }
    }
}

suspend fun signUp(email: String, nombre: String, password: String): Boolean{
    val options = AuthSignUpOptions.builder()
        .userAttribute(AuthUserAttributeKey.email(), email)
        .userAttribute(AuthUserAttributeKey.name(), nombre)
        .build()
    return try {
        val result = Amplify.Auth.signUp(email, password, options)
        Log.i("AuthQuickStart", "Result: $result")
        result.isSignUpComplete
    } catch (error: AuthException) {
        Log.e("AuthQuickStart", "Sign up failed", error)
        false
    }
}