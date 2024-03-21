package mx.ipn.escom.TTA024.Admin

import android.annotation.SuppressLint
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
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.navigation.AppNavigation
import mx.ipn.escom.TTA024.navigation.AppScreens

class AdminPrincipalActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}


@Composable
fun BotonNavegacion(imagen: Int, textoDesplegable: String, navController: NavController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .background(Color.White)
            .height(272.dp)
            .width(330.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Text(
                text = textoDesplegable,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = imagen),
                contentDescription = "usuario",
                modifier = Modifier
                    .clickable {
                        if (textoDesplegable == "Consultar Usuarios") {
                            navController.navigate(route = AppScreens.AdminUsuariosActivity.route)
                        }
                        if (textoDesplegable == "Consultar Lecciónes") {
                            navController.navigate(route = AppScreens.AdminLeccionesActivity.route)
                        }
                        if (textoDesplegable == "Consultar Módulos") {
                            navController.navigate(route = AppScreens.AdminModulosActivity.route)
                        }
                        if (textoDesplegable == "Consultar Ejercicios") {
                            navController.navigate(route = AppScreens.AdminModulosActivity.route)
                        }
                    }
                    .align(alignment = Alignment.CenterHorizontally)
                    .size(165.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.White, CircleShape)   // add a border (optional)

            )
            Spacer(modifier = Modifier.fillMaxHeight())
        }

    }
}

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
