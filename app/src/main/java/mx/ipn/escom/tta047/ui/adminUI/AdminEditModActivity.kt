package mx.ipn.escom.tta047.ui.adminUI

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.ipn.escom.tta047.R
import mx.ipn.escom.tta047.data.models.ModuloModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditModulo(navController: NavHostController, modulo: ModuloModel) {
    TopBackAppBarAdministrador(
        navController=navController,
        texto = "Editar Módulo"
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Tema: "+ modulo.tema,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp, modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 30.dp, start = 10.dp)
        )
        Text(
            text = "Título módulo: "+ modulo.nombreModulo,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp, modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 15.dp, start = 10.dp)
        )
        Text(
            text = "Selecciona una opción:",
            fontStyle = FontStyle.Italic,
            fontSize = 24.sp, modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 30.dp, start = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.leccionicon,"Consultar Lecciónes", navController,modulo)
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.ejerciciosicon,"Consultar Ejercicios",navController,modulo)
        Spacer(modifier = Modifier.height(20.dp))
    }
}
