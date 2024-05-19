package mx.ipn.escom.TTA024.ui.AdminUI

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.ui.navigation.AppScreens
import mx.ipn.escom.TTA024.ui.theme.blueButton
import mx.ipn.escom.TTA024.ui.theme.fontMonserrat
import mx.ipn.escom.TTA024.ui.theme.redButton
import mx.ipn.escom.TTA024.ui.viewmodels.AdminLeccionesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminFormLeccionComposable(navController: NavController,modulo: Modulo ,leccion: Leccion,adminLeccionesViewModel: AdminLeccionesViewModel){

    var editLeccion=true
    if (leccion?.idLeccion==0 && leccion?.tituloLeccion.equals("none")){
        editLeccion=false
    }
    Log.i("editleccion",editLeccion.toString())
    var titulo by remember {
        var titTemp:String=""
        if(editLeccion){
            titTemp= leccion.tituloLeccion
        }
        mutableStateOf(titTemp)
    }

    var descripcion by remember {
        var desTemp:String=""
        if(editLeccion){
            desTemp=leccion.descripcionLeccion
        }
        mutableStateOf(desTemp)
    }
    var nivel by remember {
        var nivelTempo:String=""
        if(editLeccion){
            nivelTempo=leccion.nivelLeccion.toString()
        }
        mutableStateOf(nivelTempo)
    }
    var recursoMultimedia by remember {
        var recursoMultimedia:String=""
        if(editLeccion){
            recursoMultimedia=leccion.recursoMultimedia
        }
        mutableStateOf(recursoMultimedia)
    }


    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
            title = {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(2.dp))
                if(editLeccion){
                    Text(text = "Editar lección", modifier= Modifier.padding(start = 30.dp))
                }else{
                    Text(text = "Agregar lección", modifier= Modifier.padding(start = 30.dp))
                }

            })
    },bottomBar = {BottomAppBar {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center){
            TextButton(
                onClick = {navController.popBackStack()},
                modifier = Modifier
                    .width(150.dp)
                    .height(58.dp)
                    .background(redButton, RoundedCornerShape(30.dp)),

                ) {
                Text(
                    text = "Cancelar",
                    color = Color.Black,
                    fontFamily = fontMonserrat,
                    fontSize = 18.sp
                )
            }
            TextButton(
                onClick = {
                    var id=-3
                    if(editLeccion){
                        id=leccion.idLeccion
                    }
                      val leccionNueva: Leccion = Leccion(id,titulo,descripcion,0,modulo.idModulo,recursoMultimedia)
                    if(editLeccion){
                        adminLeccionesViewModel.onUpdateLeccion(leccion,leccionNueva)
                    }else{
                        adminLeccionesViewModel.onCreateLeccion(leccionNueva)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(58.dp)
                    .background(blueButton, RoundedCornerShape(30.dp)),

                ) {
                Text(
                    text = "Aceptar",
                    color = Color.White,
                    fontFamily = fontMonserrat,
                    fontSize = 18.sp
                )
            }
        }
    }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ){
            Spacer(modifier = Modifier.height(80.dp))
            Box(
                modifier = Modifier
                    .width(330.dp)
                    .border(
                        width = 1.dp, // Border width
                        color = Color.LightGray, // Border color
                        shape = MaterialTheme.shapes.large // Border shape (optional)
                    )
                    .padding(16.dp)
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    OutlinedTextField(
                        value = recursoMultimedia,
                        onValueChange = { recursoMultimedia = it },
                        label = { Text("Link de video") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Titulo") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )

                }
            }
        }
    }
}