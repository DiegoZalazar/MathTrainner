package mx.ipn.escom.TTA024.ui.AdminUI

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.ui.navigation.AppScreens
import mx.ipn.escom.TTA024.ui.theme.fontMonserrat
import mx.ipn.escom.TTA024.ui.theme.greenButton


@Composable
fun BotonNavegacion(imagen: Int, textoDesplegable: String, navController: NavController, modulo: ModuloModel?=null) {
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
                            navigateToLecciones(navController,modulo)
                        }
                        if (textoDesplegable == "Consultar Módulos") {
                            navController.navigate(route = AppScreens.AdminModulosActivity.route)
                        }
                        if (textoDesplegable == "Consultar Ejercicios") {
                            navigateToEjercicios(navController,modulo)
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

fun navigateToLecciones(navController: NavController,modulo: ModuloModel?){
    val moduloJson = Gson().toJson(modulo)
    navController.navigate(route = AppScreens.AdminLeccionesActivity.route+"/$moduloJson")
}

fun navigateToEjercicios(navController: NavController,modulo: ModuloModel?){
    val moduloJson = Gson().toJson(modulo)
    print(moduloJson)
    navController.navigate(route = AppScreens.AdminEjerciciosActivity.route+"/$moduloJson")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogConfirmarAccion(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    texto: String,
    navController: NavController
) {
    val textoModifier = Modifier.padding(top = 5.dp)
    if (show) {
        AlertDialog(

            modifier = Modifier
                .height(400.dp)
                .width(351.dp)
                .clip(RoundedCornerShape(28.dp)),
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Column(
                    modifier = Modifier
                        .height(68.dp)
                        .width(314.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = texto,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        textAlign = TextAlign.Center
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.successicon),
                    contentDescription = texto,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .size(165.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)

                )
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = { onDismiss()
                                navController.popBackStack()
                              },
                    modifier = Modifier
                        .width(300.dp)
                        .height(58.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(greenButton, RoundedCornerShape(30.dp)),

                    ) {
                    Text(
                        text = "Aceptar",
                        color = Color.Black,
                        fontFamily = fontMonserrat,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBackAppBarAdministrador(navController: NavController, texto: String) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
            title = {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }.padding(2.dp))
                Text(text = texto, modifier= Modifier.padding(start = 30.dp))
            })
    }) {

    }
}