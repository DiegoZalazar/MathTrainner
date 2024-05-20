package mx.ipn.escom.tta047.ui.estudianteUI.estudianteconfig

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import mx.ipn.escom.tta047.ui.StudentScreens
import mx.ipn.escom.tta047.ui.estudianteUI.home.StudentHomeViewModel
import mx.ipn.escom.tta047.ui.smallcomponents.ConfirmAlert
import kotlin.reflect.KSuspendFunction0

@Composable
fun EstudianteConfig(
    navController: NavController,
    estudianteConfigVM: EstudianteConfigVM = viewModel(),
    studentVM: StudentHomeViewModel,
    modifier: Modifier = Modifier,
) {
    var estudianteConfigUIState = estudianteConfigVM.estudianteConfigUIState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = "Configuracion",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                },
                modifier = modifier,
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        }
    ) {
        when(estudianteConfigUIState){
            is EstudianteConfigUIState.Error -> EstudianteConfigError(padding = it) {
                navController.navigateUp()
            }
            is EstudianteConfigUIState.Loading -> Row(
                Modifier
                    .fillMaxWidth()
                    .padding(it), horizontalArrangement = Arrangement.Center){ CircularProgressIndicator()}
            is EstudianteConfigUIState.Success -> EstudianteConfigSuccess(
                nombre = estudianteConfigUIState.nombre,
                email = estudianteConfigUIState.email,
                modifier = modifier,
                padding = it,
                navToEditarNombre = { navController.navigate(StudentScreens.StudentConfigName.name) },
                navToResetPswd = {

                    navController.navigate(StudentScreens.StudentConfigResetPswd.name)
                },
                navToLogin = {
                    studentVM.resetDimissExamDone()
                    navController.navigate("login"){
                        popUpTo("student") { inclusive = true }
                    }
                },
                borrarAvance = studentVM::borrarAvance,
                navToExam = {
                    navController.navigate(StudentScreens.ExamInfo.name)
                }
            )
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

@Composable
fun EstudianteConfigError(
    padding: PaddingValues,
    regresar: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Ocurrio un error")
        Button(
            onClick = regresar
        ) {
            Text("Regresar")
        }
    }
}

@Composable
fun EstudianteConfigSuccess(
    nombre: String,
    email: String,
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navToEditarNombre: () -> Unit,
    navToResetPswd: () -> Unit,
    navToLogin: () -> Unit,
    navToExam: () -> Unit,
    borrarAvance: KSuspendFunction0<Unit>
) {
    val context = LocalContext.current
    var loadResetPassword by remember { mutableStateOf(false) }

    if(loadResetPassword){
        Dialog(onDismissRequest = {}){
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = true) {
            val result = resetPassword(email)
            if(result){
                navToResetPswd()
                Toast.makeText(context, "Codigo enviado a $email", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Error, usuario no registrado", Toast.LENGTH_SHORT).show()
            }
            loadResetPassword = false
        }
    }
    var closeSesionLoading by remember { mutableStateOf(false) }
    if(closeSesionLoading){
        LaunchedEffect(key1 = true) {
            try {
                Amplify.Auth.signOut()
            } catch (error: AuthException) {
                Log.e("AmplifyQuickstart", "Failed to sign out auth session", error)
                navToLogin()
            }
            navToLogin()
            closeSesionLoading = false
        }
    }

    var borrarAvance by remember { mutableStateOf(false) }
    var borrarAvanceLoading by remember { mutableStateOf(false) }
    if(borrarAvance){
        ConfirmAlert(
            confirmQuestion = "¿Estás seguro?",
            confirmAlertMsg = "Se perderá todo tu avance en los módulos.",
            onDimiss = {borrarAvance = false},
            onConfirm = {
                borrarAvanceLoading = true
            }
        )
    }
    if(borrarAvanceLoading){
        LaunchedEffect(key1 = true) {
            try {
                borrarAvance()
                Toast.makeText(context, "Avance borrado", Toast.LENGTH_SHORT).show()
            } catch (error: Exception) {
                Log.e("EstudianteConfig", "Error al borrar avance: $error")
                Toast.makeText(context, "Error al borrar avance", Toast.LENGTH_SHORT).show()
            }
            borrarAvanceLoading = false
            borrarAvance = false

        }
    }
    Column(
        modifier = modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "person icon",
            modifier = Modifier.size(150.dp)
        )
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            val textStyle = MaterialTheme.typography.bodyLarge
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Nombre:",
                    style = textStyle,
                )
                Text(
                    text = nombre,
                    style = textStyle,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Email:",
                    style = textStyle
                )
                Text(
                    text = email,
                    style = textStyle,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
        }
        Button(
            onClick = navToEditarNombre,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Editar nombre")
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { loadResetPassword = true },
            modifier = Modifier.width(200.dp)
        ) {
            Text("Cambiar contraseña")
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = navToExam,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Realizar Examen")
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { borrarAvance = true },
            modifier = Modifier.width(200.dp)
        ) {
            Text("Borrar avance")
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = { closeSesionLoading = true },
            modifier = Modifier.width(200.dp),
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.error,
                containerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Text("Cerrar sesion")
        }
    }
}