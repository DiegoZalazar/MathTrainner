package mx.ipn.escom.tta047.ui.adminUI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.gson.Gson
import mx.ipn.escom.tta047.R
import mx.ipn.escom.tta047.domain.model.Usuario
import mx.ipn.escom.tta047.ui.navigation.AppScreens
import mx.ipn.escom.tta047.ui.theme.blueButton
import mx.ipn.escom.tta047.ui.theme.fontMonserrat
import mx.ipn.escom.tta047.ui.theme.redButton
import mx.ipn.escom.tta047.ui.viewmodels.AdminUsuariosViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuariosComposable(navController: NavHostController,
                       adminUsuariosViewModel: AdminUsuariosViewModel
) {
    // Just a fake data... a Pair of Int and String
    val headers = arrayOf("Email", "Nombre", "Eliminar", "Editar")
    /*val estudiante1 = Usuario(1, "adal", "danidc")
    val estudiante2 = Usuario(2, "adal2", "danidc2")
    val estudiante3 = Usuario(3, "adal3", "danidc3")
    val estudianteList = listOf<Usuario>(estudiante1, estudiante2, estudiante3)*/

    adminUsuariosViewModel.onCreate()
    val estudianteList by adminUsuariosViewModel.usuarioModel.observeAsState(initial = arrayListOf())

    // Each cell of a column must have the same weight.
    val ancho = 300
    val columsWeight = (ancho / headers.size).toFloat()
    // The LazyColumn will be our table. Notice the use of the weights below

    TopBackAppBarAdministrador(navController = navController, texto = "Estudiantes")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(60.dp))
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
            items(estudianteList) {
                val usuario = it

                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = usuario.sub, weight = columsWeight)
                    TableCell(text = usuario.name, weight = columsWeight)
                    TableCellDeleteImageEstudiante(
                        image = R.drawable.deleteicon,
                        tamano = columsWeight,
                        usuario = usuario,
                        adminUsuariosViewModel
                    )
                    TableCellEditImageEstudiante(
                        image = R.drawable.editicon,
                        tamano = columsWeight,
                        navController = navController,
                        usuario = usuario,
                        adminUsuariosViewModel
                    )

                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    val context= LocalContext.current
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .height(30.dp),
    ) {
        var texto:String=text
        if(text.length>=10){
            texto=text.substring(0,10)+".."
        }

        ClickableText(
            text = AnnotatedString(texto) ,
            modifier = Modifier
                .padding(start = 2.dp)
                .align(Alignment.CenterStart),
            onClick = {
                Toast.makeText(context,text,Toast.LENGTH_LONG).show()
            })
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogEliminarUsuario(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    usuario: Usuario,
    adminUsuariosViewModel: AdminUsuariosViewModel
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
                        text = "¿Seguro que seas eliminar al usuario?",
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
                        text = "Nombre: " + usuario.name,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "ID: " + usuario.user_id.toString(),
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        modifier = textoModifier
                    )
                    Text(
                        text = "Correo: " + usuario.email,
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
                        adminUsuariosViewModel.onDeleteUsuario(usuario = usuario)
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
fun RowScope.TableCellDeleteImageEstudiante(
    image: Int,
    tamano: Float,
    usuario: Usuario,
    adminUsuariosViewModel: AdminUsuariosViewModel,
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
            contentDescription = "usuario",
            modifier = Modifier
                .clickable {
                    showDelete = true

                }
                .align(Alignment.Center)
        )
    }
    DialogEliminarUsuario(showDelete, { showDelete = false }, { showDelete = false }, usuario,adminUsuariosViewModel)

}


fun navigateToEstudiante(navController: NavController,estudiante: Usuario){
    estudiante.user_id=estudiante.sub
    val estudianteJson = Gson().toJson(estudiante)
    navController.navigate(route = AppScreens.AdminEditUserActivity.route+"/$estudianteJson")
}
@Composable
fun RowScope.TableCellEditImageEstudiante(
    image: Int,
    tamano: Float,
    navController: NavController,
    usuario: Usuario,
    adminUsuariosViewModel: AdminUsuariosViewModel
) {

    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .weight(tamano)
            .height(30.dp)
    ) {

        Image(
            painter = painterResource(id = image),
            contentDescription = "usuario",
            modifier = Modifier
                .clickable {
                    navigateToEstudiante(navController,usuario)
                }
                .align(Alignment.Center)
        )
    }

}

