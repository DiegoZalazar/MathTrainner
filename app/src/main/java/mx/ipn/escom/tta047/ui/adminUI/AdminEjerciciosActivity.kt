package mx.ipn.escom.tta047

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import com.google.gson.Gson
import mx.ipn.escom.tta047.ui.adminUI.TableCell
import mx.ipn.escom.tta047.ui.adminUI.TopBackAppBarAdministrador
import mx.ipn.escom.tta047.domain.model.Ejercicio
import mx.ipn.escom.tta047.domain.model.Modulo
import mx.ipn.escom.tta047.ui.navigation.AppScreens
import mx.ipn.escom.tta047.ui.theme.blueButton
import mx.ipn.escom.tta047.ui.theme.fontMonserrat
import mx.ipn.escom.tta047.ui.theme.redButton
import mx.ipn.escom.tta047.ui.viewmodels.AdminEjerciciosViewModel

@Composable
fun EjerciciosAdminComposable(navController: NavHostController, modulo: Modulo, adminEjerciciosViewModel: AdminEjerciciosViewModel){
    // Just a fake data... a Pair of Int and String
    val headers = arrayOf("Id", "Planteamiento","Nivel","Eliminar","Editar")
    /*val ejercicio1 = Ejercicio(1,40,2,"selecciona","u=x-1","u=x;u=1;u=x*x","",1,1)
    val ejercicio2 = Ejercicio(2,90,3,"Une las columnas","","","a:A;b:B;c:C;d:D",1,1)
    val ejercicio3 = Ejercicio(3,20,1,"Completa","u=x-4","","",1,1)
    val ejercicioList = listOf<Ejercicio>(ejercicio1, ejercicio2, ejercicio3)*/
    // Each cell of a column must have the same weight.
    adminEjerciciosViewModel.onCreate(modulo)
    val ejercicioList by adminEjerciciosViewModel.ejercicioModel.observeAsState(initial = arrayListOf())

    val ancho = 300
    val columsWeight = (ancho / headers.size).toFloat()
    // The LazyColumn will be our table. Notice the use of the weights below

    TopBackAppBarAdministrador(navController = navController, texto = "Ejercicios")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Tema: "+ modulo.tema,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp, modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 30.dp, start = 10.dp)
        )
        Text(
            text = "Título modulo: "+ modulo.nombreModulo,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp, modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 15.dp, start = 10.dp)
        )
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Here is the header
            item() {

                Row(Modifier.background(Color.Gray)) {
                    for (header in headers) {
                        TableCell(text = header, weight = columsWeight)
                    }
                }

            }
            // Here are all the lines of your table.
            items(ejercicioList) {
                val ejercicio = it

                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = ejercicio.idEjercicio.toString(), weight = columsWeight)
                    TableCell(text = ejercicio.planteamientoEjercicio, weight = columsWeight)
                    TableCell(text = ejercicio.nivelEjercicio.toString(), weight = columsWeight)
                    TableCellDeleteImageEjercicio(
                        image = R.drawable.deleteicon,
                        tamano = columsWeight,
                        ejercicio = ejercicio,
                        modulo,
                        adminEjerciciosViewModel
                    )
                    TableCellEditImageEjercicio(
                        image = R.drawable.editicon,
                        tamano = columsWeight,
                        navController = navController,
                        ejercicio = ejercicio,
                        modulo = modulo
                    )

                }
            }
        }
    }
    buttonAddEjercicio(navController, modulo)
}

@Composable
fun RowScope.TableCellDeleteImageEjercicio(
    image: Int,
    tamano: Float,
    ejercicio: Ejercicio,
    modulo: Modulo,
    adminEjerciciosViewModel: AdminEjerciciosViewModel

) {
    val context = LocalContext.current
    var showDelete by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(tamano)
            .height(30.dp)
    ) {

        Image(
            painter = painterResource(id = image),
            contentDescription = "ejercicio",
            modifier = Modifier
                .clickable {
                    showDelete = true
                }
                .align(Alignment.Center)
        )
    }
    DialogEliminarEjercicio(showDelete, { showDelete = false }, { showDelete = false }, ejercicio,modulo ,adminEjerciciosViewModel)

}
@Composable
fun RowScope.TableCellEditImageEjercicio(
    image: Int,
    tamano: Float,
    navController: NavController,
    ejercicio: Ejercicio,
    modulo: Modulo,
) {

    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(tamano)
            .height(30.dp)
    ) {

        Image(
            painter = painterResource(id = image),
            contentDescription = "ejercicio",
            modifier = Modifier
                .clickable {
                    navigateToEditEjercicio(navController,ejercicio,modulo)
                }
                .align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogEliminarEjercicio(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    ejercicio: Ejercicio,
    modulo: Modulo,
    adminEjerciciosViewModel: AdminEjerciciosViewModel
) {
    val textoModifier = Modifier.padding(top = 5.dp)
    if (show) {
        AlertDialog(

            modifier = Modifier
                .height(506.dp)
                .width(351.dp)
                .clip(RoundedCornerShape(28.dp)),
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .height(68.dp)
                        .width(314.dp)
                ) {
                    Text(
                        text = "¿Seguro que seas eliminar al ejercicio?",
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(296.dp)
                        .height(168.dp)
                ) {
                    Text(
                        text = "ID: " + ejercicio.idEjercicio,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Planteamiento: " + ejercicio.planteamientoEjercicio,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Cuerpo (Integral): " + ejercicio.cuerpo,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Tipo: " + ejercicio.tipo,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Tiempo: " + ejercicio.tiempoEjercicio,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Nivel: " + ejercicio.nivelEjercicio,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Spacer(modifier = Modifier.fillMaxHeight())
                }
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = {
                        onConfirm()
                        deleteEjercicio(modulo,ejercicio, adminEjerciciosViewModel)
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(58.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            width = 3.dp,
                            color = redButton,
                            shape = RoundedCornerShape(30.dp)
                        ),
                ) {
                    Text(
                        text = "Si",
                        fontFamily = fontMonserrat,
                        color = redButton,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .width(300.dp)
                        .height(58.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(blueButton, RoundedCornerShape(30.dp)),

                    ) {
                    Text(
                        text = "No",
                        color = Color.White,
                        fontFamily = fontMonserrat,
                        fontSize = 18.sp
                    )
                }


                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}

@Composable
fun buttonAddEjercicio(navController: NavController, modulo: Modulo) {

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 15.dp, end = 15.dp),horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom) {
        FloatingActionButton(onClick =
        {
            var ejercicio = Ejercicio(0,"","",0,0,"","","","",0,0)
            val ejercicioJson = Gson().toJson(ejercicio)
            val moduloJson = Gson().toJson(modulo)
            navController.navigate(route = AppScreens.AdminFormEjercicioActivity.route+"/$moduloJson/$ejercicioJson")
        }
        )
        {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}
fun deleteEjercicio(modulo: Modulo,ejercicio: Ejercicio,adminEjerciciosViewModel: AdminEjerciciosViewModel) {
    adminEjerciciosViewModel.onDeleteEjercicioByModulo(modulo,ejercicio)
}

fun navigateToEditEjercicio(navController: NavController,ejercicio: Ejercicio,modulo: Modulo){
    /*ejercicio.cuerpo= String(ejercicio.cuerpo.toByteArray(), Charsets.UTF_8)
    ejercicio.paresCorrectosEjercicio= String(ejercicio.paresCorrectosEjercicio.toByteArray(), Charsets.UTF_8)
    ejercicio.respCorrectaEjercicio= String(ejercicio.respCorrectaEjercicio.toByteArray(), Charsets.UTF_8)
    ejercicio.respIncorrectasEjercicio= String(ejercicio.respIncorrectasEjercicio.toByteArray(), Charsets.UTF_8)*/
    /*ejercicio.cuerpo= URLEncoder.encode(ejercicio.cuerpo, StandardCharsets.UTF_8.toString())
    ejercicio.paresCorrectosEjercicio= URLEncoder.encode(ejercicio.paresCorrectosEjercicio, StandardCharsets.UTF_8.toString())
    ejercicio.respCorrectaEjercicio= URLEncoder.encode(ejercicio.respCorrectaEjercicio, StandardCharsets.UTF_8.toString())
    ejercicio.respIncorrectasEjercicio = URLEncoder.encode(ejercicio.respCorrectaEjercicio, StandardCharsets.UTF_8.toString())*/
    Log.i("ejercicios",ejercicio.toString())
    val ejercicioJson = Uri.encode(Gson().toJson(ejercicio))
    val moduloJson = Gson().toJson(modulo)
    navController.navigate(route = AppScreens.AdminFormEjercicioActivity.route+"/$moduloJson/$ejercicioJson")
}
