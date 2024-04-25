package mx.ipn.escom.TTA024.ui.AdminUI

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.navigation.AppNavigation




@Composable
fun BodyPrincipalAdministrador(navController: NavController) {


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
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalAdministrador(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Bienvenido, ")
            }
        )
    }
    ) {
        BodyPrincipalAdministrador(navController = navController)
    }

}
