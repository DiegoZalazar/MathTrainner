package mx.ipn.escom.TTA024.AdminUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.data.models.Estudiante


@Composable
fun EditUserComposable(navController: NavController, estudiante: Estudiante) {
        var name by remember { mutableStateOf(estudiante.nombreUsuario) }
        var email by remember { mutableStateOf(estudiante.correoEstudiante) }
        var pswd by remember { mutableStateOf(estudiante.contrasenaEstudiante) }
        var pswdConfirm by remember { mutableStateOf("") }
        var pswdVisible by remember { mutableStateOf(false) }
        var pswdConfirmVisible by remember { mutableStateOf(false) }
        var isErrorPassword by rememberSaveable { mutableStateOf(true) }
        var isErrorName by rememberSaveable { mutableStateOf(false) }
        var isErrorCorreo by rememberSaveable { mutableStateOf(false) }


    var showSuccess by rememberSaveable {
        mutableStateOf(false)
    }

        fun validatePasswords(){
            isErrorPassword = !pswd.equals(pswdConfirm)
        }


    TopBackAppBarAdministrador(navController = navController, texto = "Editar usuario")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = painterResource(id = R.drawable.editusericon),
                    contentDescription = "usuario",
                    modifier = Modifier
                        .size(77.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)
                        .padding(start = 20.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.editicon),
                    contentDescription = "usuario",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)
                )
            }
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
                        text = "Editar usuario",
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )

                    OutlinedTextField(
                        value = pswd,
                        onValueChange = { pswd = it },
                        label = { Text("Contraseña") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (pswdVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (pswdVisible)
                                Icons.Default.Check
                            else Icons.Default.Clear

                            // Please provide localized description for accessibility services
                            val description = if (pswdVisible) "Hide password" else "Show password"

                            IconButton(onClick = {pswdVisible = !pswdVisible}){
                                Icon(imageVector  = image, description)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = pswdConfirm,
                        onValueChange = { pswdConfirm = it
                                        validatePasswords()
                                        },
                        label = { Text("Confirmar Contraseña") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (pswdConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (pswdConfirmVisible)
                                Icons.Default.Check
                            else Icons.Default.Clear

                            // Please provide localized description for accessibility services
                            val description = if (pswdConfirmVisible) "Hide password" else "Show password"

                            IconButton(onClick = {pswdConfirmVisible = !pswdConfirmVisible}){
                                Icon(imageVector  = image, description)
                            }
                        },
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        isError = isErrorPassword,
                        supportingText = {
                            if (isErrorPassword) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Las contraseñas deben ser iguales",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if(!isErrorPassword){
                                estudiante.nombreEstudiante = name
                                estudiante.contrasenaEstudiante= pswd
                                estudiante.correoEstudiante= email
                                EditarUsuario(estudiante)
                                showSuccess=true
                            }

                        },
                        modifier = Modifier
                            .widthIn(min = 250.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Editar cuenta")
                    }
                    DialogConfirmarAccion(
                        showSuccess, { showSuccess = false }, { showSuccess = false },
                        texto="Estudiante eliminado",
                        navController = navController
                    )
                }
            }
        }
}



fun EditarUsuario(estudiante: Estudiante, ){

}