package mx.ipn.escom.TTA024.Admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.ipn.escom.TTA024.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditModulo(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Editar Módulo, ")
            }
        )
        Text(
            text = "Tema: ",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp, modifier = Modifier.align(alignment = Alignment.Start).padding(top = 30.dp, start = 10.dp)
        )
        Text(
            text = "Titulo: ",
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp, modifier = Modifier.align(alignment = Alignment.Start).padding(top = 30.dp, start = 10.dp)
        )
        Text(
            text = "Selecciona una opción:",
            fontStyle = FontStyle.Italic,
            fontSize = 24.sp, modifier = Modifier.align(alignment = Alignment.Start).padding(top = 30.dp, start = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.leccionicon,"Consultar Lecciónes", navController)
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.ejerciciosicon,"Consultar Ejercicios",navController)
        Spacer(modifier = Modifier.height(20.dp))
    }
}
