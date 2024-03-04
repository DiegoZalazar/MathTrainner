package mx.ipn.escom.TTA024.Admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.TTA024.Admin.ui.theme.MathTrainerTheme
import mx.ipn.escom.TTA024.R

class AdminEditModActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditModulo()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun EditModulo() {
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
        BotonNavegacion(R.drawable.leccionicon,"Consultar Lecciónes", AdminUsuariosActivity::class.java)
        Spacer(modifier = Modifier.height(20.dp))
        BotonNavegacion(R.drawable.ejerciciosicon,"Consultar Ejercicios",AdminModulosActivity::class.java)
        Spacer(modifier = Modifier.height(20.dp))
    }
}
