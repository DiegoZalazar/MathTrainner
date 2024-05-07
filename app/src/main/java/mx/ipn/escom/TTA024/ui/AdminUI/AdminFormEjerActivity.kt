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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.ui.theme.blueButton
import mx.ipn.escom.TTA024.ui.theme.fontMonserrat
import mx.ipn.escom.TTA024.ui.theme.redButton
import mx.ipn.escom.TTA024.ui.viewmodels.AdminEjerciciosViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminFormEjercicioComposable(navController: NavController,modulo: Modulo ,ejercicio: Ejercicio,adminEjerciciosViewModel: AdminEjerciciosViewModel){
    var editEjercicio=true
    if (ejercicio?.idEjercicio==0 && ejercicio?.planteamientoEjercicio.equals("none")){
        editEjercicio=false
    }
    Log.i("editEjercicio",editEjercicio.toString())
    var tiempoEjercicio by remember {
        var titTemp:String=""
        if(editEjercicio){
            titTemp= ejercicio.tiempoEjercicio.toString()
        }
        mutableStateOf(titTemp)
    }

    var nivelEjercicio by remember {
        var desTemp:String="Facil"
        if(editEjercicio){
            val lvl=ejercicio.nivelEjercicio.toString()
            if(lvl=="1"){
                desTemp="Facil"
            }
            if(lvl=="2"){
                desTemp="Intermedio"
            }
            if(lvl=="3"){
                desTemp="Dificil"
            }
        }
        mutableStateOf(desTemp)
    }
    var planteamiento by remember {
        var nivelTempo:String=""
        if(editEjercicio){
            nivelTempo=ejercicio.planteamientoEjercicio
        }
        mutableStateOf(nivelTempo)
    }

    var tipoEjercicio by remember {
        var tipoEjercicio:String="Opción multiple"
        if (editEjercicio){
            if((ejercicio.respCorrectaEjercicio!="" && ejercicio.respCorrectaEjercicio!="none") && (ejercicio.respIncorrectasEjercicio!="" && ejercicio.respIncorrectasEjercicio!="none")){
                tipoEjercicio="Opción multiple"
            }
            if((ejercicio.respCorrectaEjercicio!="" && ejercicio.respCorrectaEjercicio!="none") && (ejercicio.respIncorrectasEjercicio=="" && ejercicio.respIncorrectasEjercicio=="none")){
                tipoEjercicio="Completar respuesta"
            }
            if(ejercicio.paresCorrectosEjercicio!="" && ejercicio.paresCorrectosEjercicio!="none"){
                tipoEjercicio="Relacionar columnas"
            }
        }
        mutableStateOf(tipoEjercicio)
    }

    var respCorrecta by remember {
        var respTemp:String=""
        if(tipoEjercicio=="opcionMultiple"){
            respTemp=ejercicio.respCorrectaEjercicio
        }
        if(tipoEjercicio=="completarRespuesta"){
            respTemp=ejercicio.respCorrectaEjercicio
        }
        mutableStateOf(respTemp)
    }

    var respIncorrectas by remember {
        var respTemp:String=""
        if(tipoEjercicio=="opcionMultiple"){
            respTemp=ejercicio.respIncorrectasEjercicio
        }
        mutableStateOf(respTemp)
    }

    var paresCorrectos by remember {
        var respTemp:String=""
        if(tipoEjercicio=="relacionarColumnas"){
            respTemp=ejercicio.paresCorrectosEjercicio
        }
        mutableStateOf(respTemp)
    }

    val niveles = listOf("Facil","Intermedio","Dificil")
    val tiposEjercicios = listOf("Opción multiple","Relacionar columnas","Completar respuesta")
    var expanded by remember {
        mutableStateOf(false)
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
                if(editEjercicio){
                    Text(text = "Editar ejercicio", modifier= Modifier.padding(start = 30.dp))
                }else{
                    Text(text = "Agregar ejercicio", modifier= Modifier.padding(start = 30.dp))
                }

            })
    },bottomBar = {
        BottomAppBar {
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
                    var id=0
                    if(editEjercicio){
                        id=ejercicio.idEjercicio
                    }
                    var ejercicioNuevo: Ejercicio? = null
                    if(tipoEjercicio=="opcionMultiple"){
                        ejercicioNuevo=Ejercicio(id,tiempoEjercicio.toInt(),nivelEjercicio.toInt(),planteamiento,respCorrecta,respIncorrectas,"",1, modulo.idModulo)
                    }
                    if(tipoEjercicio=="completarRespuesta"){
                        ejercicioNuevo=Ejercicio(id,tiempoEjercicio.toInt(),nivelEjercicio.toInt(),planteamiento,respCorrecta,"","",1, modulo.idModulo)
                    }
                    if(tipoEjercicio=="relacionarColumnas"){
                        ejercicioNuevo=Ejercicio(id,tiempoEjercicio.toInt(),nivelEjercicio.toInt(),planteamiento,"","",paresCorrectos,1, modulo.idModulo)
                    }

                    if(editEjercicio){
                        adminEjerciciosViewModel.onUpdateEjercicio(ejercicio, ejercicioNuevo!!)
                    }else{
                        adminEjerciciosViewModel.onCreateEjercicio(ejercicioNuevo!!)
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
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    OutlinedTextField(
                        value = tiempoEjercicio,
                        onValueChange = { tiempoEjercicio = it },
                        label = { Text("Tiempo estimado para la realización del ejercicio (en segundos)") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                    ////MENU NIVEL///////
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = nivelEjercicio ,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            niveles.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        nivelEjercicio = item
                                        expanded = false
                                        //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    ///////////////////
                    ////MENU TIPO DE EJERCICIO///////
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = tipoEjercicio ,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            tiposEjercicios.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        tipoEjercicio = item
                                        expanded = false
                                        //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    ///////////////////
                    OutlinedTextField(
                        value = planteamiento,
                        onValueChange = { planteamiento = it },
                        label = { Text("Planteamiento ejercicio") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                    if(tipoEjercicio=="Relacionar columnas"){
                        var pares: List<String> =paresCorrectos.split(";")
                        var incisos by remember {
                            mutableStateOf(mutableListOf<String>())
                        }
                        pares.forEach{
                            val preguntaRespuesta= it.split(":")
                            preguntaRespuesta.forEach{
                                incisos.add(it)
                            }
                        }
                        for(i in 0..4){
                            Row(modifier=Modifier.fillMaxWidth()){
                                OutlinedTextField(
                                    value = incisos[i*2],
                                    onValueChange = {  incisos[i*2]=it },
                                    label = { Text("Nivel") },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier
                                        .padding(vertical = 16.dp)
                                )

                                OutlinedTextField(
                                    value = incisos[i*2-1],
                                    onValueChange = {  incisos[i*2+1]=it },
                                    label = { Text("Nivel") },
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
        }
    }
}