package mx.ipn.escom.TTA024.ui.AdminUI

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
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.data.models.UsuarioModel
import mx.ipn.escom.TTA024.domain.model.Usuario
import mx.ipn.escom.TTA024.ui.viewmodels.AdminUsuariosViewModel


@Composable
fun EditUserComposable(
    navController: NavController,
    estudiante: Usuario,
    adminUsuariosViewModel: AdminUsuariosViewModel
) {
        var name by remember { mutableStateOf(estudiante.name) }
        var email by remember { mutableStateOf(estudiante.email) }
        var isErrorName by rememberSaveable { mutableStateOf(false) }
        var isErrorCorreo by rememberSaveable { mutableStateOf(false) }


    var showSuccess by rememberSaveable {
        mutableStateOf(false)
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


                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if(!isErrorCorreo || !isErrorName){
                                estudiante.name = name
                                estudiante.email= email
                                adminUsuariosViewModel.onUpdateUsuario(estudiante)
                                showSuccess=true
                            }

                        },
                        modifier = Modifier
                            .widthIn(min = 250.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Editar cuenta")
                    }
                    /*DialogConfirmarAccion(
                        showSuccess, { showSuccess = false }, { showSuccess = false },
                        texto="Estudiante eliminado",
                        navController = navController
                    )*/
                }
            }
        }
}



fun EditarUsuario(estudiante: Usuario){

}