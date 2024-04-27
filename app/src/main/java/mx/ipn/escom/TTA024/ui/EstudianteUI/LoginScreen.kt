package mx.ipn.escom.TTA024.ui.EstudianteUI

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun LoginScreen(
    navigateToHome: (String,String) -> Unit,
    navigateToSignUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var usr by remember { mutableStateOf("") }
    var pswd by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MathTrainer",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 32.sp,
                color = Color(0xFFD62839),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .width(330.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.large
                )
                .padding(16.dp)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a MathTrainer",
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = usr, onValueChange = { usr = it },
                label = { Text("Correo") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            OutlinedTextField(
                value = pswd, onValueChange = { pswd = it },
                label = { Text("Contraseña") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Default.Check
                    else Icons.Default.Clear

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = navigateToSignUp,
                modifier = Modifier    // dem: Si da error, cambia a modifier con minuscula
                    .widthIn(min = 250.dp)
                    .padding(vertical = 8.dp)
            ) {
               Text("Registrarse")
            }
            Button(onClick = { navigateToHome(usr,pswd) },
                modifier = Modifier
                    .widthIn(min = 250.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Iniciar Sesion")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "¿Olvidaste tu contraseña?",
                modifier = Modifier.clickable( onClick = { /*TODO: navigateToForgotPswd*/})
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("¿No tienes una cuenta?")
        Text(
            text = "Registrate",
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.clickable( onClick = navigateToSignUp )
        )
    }

}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun LoginPreview(){
    MathTrainerTheme {
        LoginScreen(navigateToHome = {a,b -> })
    }
}