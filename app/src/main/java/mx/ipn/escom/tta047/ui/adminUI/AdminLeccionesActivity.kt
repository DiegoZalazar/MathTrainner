package mx.ipn.escom.tta047

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import mx.ipn.escom.tta047.domain.model.Leccion
import mx.ipn.escom.tta047.domain.model.Modulo
import mx.ipn.escom.tta047.ui.navigation.AppScreens
import mx.ipn.escom.tta047.ui.theme.blueButton
import mx.ipn.escom.tta047.ui.theme.fontMonserrat
import mx.ipn.escom.tta047.ui.theme.redButton
import mx.ipn.escom.tta047.ui.viewmodels.AdminLeccionesViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LeccionesAdminComposable(
    navController: NavHostController,
    modulo: Modulo,
    adminLeccionesViewModel: AdminLeccionesViewModel
){
    // Just a fake data... a Pair of Int and String
    val headers = arrayOf("Id", "Titulo","Eliminar","Editar")
    /*val leccion1 = Leccion(1,"Integracion partes","Paso 1: integrar",3,1,"none")
    val leccion2 = Leccion(1,"Integracion cambio variable","Paso 1: Cambiar variable",2,1,"none")
    val leccion3 = Leccion(1,"Derivacion regla de cadena","Paso 1: Formular",1,1,"none")
    val leccionesList = listOf<Leccion>(leccion1, leccion2, leccion3)*/

    adminLeccionesViewModel.onCreate(modulo)
    val leccionesList by adminLeccionesViewModel.leccionModel.observeAsState(initial = arrayListOf())

    // Each cell of a column must have the same weight.
    val ancho = 300
    val columsWeight = (ancho / headers.size).toFloat()
    // The LazyColumn will be our table. Notice the use of the weights below

    TopBackAppBarAdministrador(navController = navController, texto = "Lecciones")

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
            item() {

                Row(Modifier.background(Color.Gray)) {
                    for (header in headers) {
                        TableCell(text = header, weight = columsWeight)
                    }
                }

            }
            // Here are all the lines of your table.
            items(leccionesList) {
                val leccion = it

                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = leccion.idLeccion.toString(), weight = columsWeight)
                    TableCell(text = leccion.tituloLeccion, weight = columsWeight)
                    TableCellDeleteImageLeccion(
                        image = R.drawable.deleteicon,
                        tamano = columsWeight,
                        leccion = leccion,
                        adminLeccionesViewModel,
                        modulo
                    )
                    TableCellEditImageLeccion(
                        image = R.drawable.editicon,
                        tamano = columsWeight,
                        navController = navController,
                        leccion = leccion,
                        modulo = modulo,
                        adminLeccionesViewModel
                    )

                }
            }
        }
    }
    buttonAddLeccion(navController,modulo)
}

@Composable
fun RowScope.TableCellDeleteImageLeccion(
    image: Int,
    tamano: Float,
    leccion: Leccion,
    adminLeccionesViewModel: AdminLeccionesViewModel,
    modulo: Modulo
) {
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
            contentDescription = "leccion",
            modifier = Modifier
                .clickable {
                    showDelete = true
                }
                .align(Alignment.Center)
        )
    }
    DialogEliminarLeccion(showDelete, { showDelete = false }, { showDelete = false }, leccion,adminLeccionesViewModel,modulo)

}
@Composable
fun RowScope.TableCellEditImageLeccion(
    image: Int,
    tamano: Float,
    navController: NavController,
    leccion: Leccion,
    modulo: Modulo,
    adminLeccionesViewModel: AdminLeccionesViewModel
) {
    var showEditLeccion by remember {
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
            contentDescription = "leccion",
            modifier = Modifier
                .clickable {
                    //showEditLeccion = true
                    navigateToEditLeccion(navController,modulo,leccion)

                }
                .align(Alignment.Center)
        )
    }
    /*if(showEditLeccion){
        AdminFormLeccionComposable(
            navController = navController,
            modulo = modulo,
            leccion = leccion,
            adminLeccionesViewModel = adminLeccionesViewModel
        )
    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogEliminarLeccion(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    leccion: Leccion,
    adminLeccionesViewModel: AdminLeccionesViewModel,
    modulo: Modulo
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
                        text = "¿Seguro que seas eliminar la lección?",
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
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "ID: " + leccion.idLeccion,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Titulo: " + leccion.tituloLeccion,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Recurso Multimedia: " + leccion.recursoMultimedia,
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
                        deleteLeccion(modulo,leccion,adminLeccionesViewModel)
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
fun buttonAddLeccion(navController: NavController, modulo: Modulo) {

    Column( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 15.dp, end = 15.dp),horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom) {
        FloatingActionButton(onClick =
        {
            var leccion: Leccion? = Leccion(0,"none","none",0,0,"none")
            val leccionJson = Gson().toJson(leccion)
            val moduloJson = Gson().toJson(modulo)
            navController.navigate(route = AppScreens.AdminFormLeccActivity.route+"/$moduloJson/$leccionJson")
        }
        )
        {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

fun deleteLeccion(modulo: Modulo,leccion: Leccion, adminLeccionesViewModel: AdminLeccionesViewModel) {
    adminLeccionesViewModel.onDeleteLeccionByModulo(modulo,leccion)
}

fun navigateToEditLeccion(navController: NavController,modulo: Modulo ,leccion: Leccion){
    leccion.recursoMultimedia= URLEncoder.encode(leccion.recursoMultimedia, StandardCharsets.UTF_8.toString())
    val leccionJson = Gson().toJson(leccion)
    val moduloJson = Gson().toJson(modulo)
    navController.navigate(route = AppScreens.AdminFormLeccActivity.route+"/$moduloJson/$leccionJson")
}
