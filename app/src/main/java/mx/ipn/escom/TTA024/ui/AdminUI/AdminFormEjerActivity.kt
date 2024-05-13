package mx.ipn.escom.TTA024.ui.AdminUI

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc.Dialog
import mx.ipn.escom.TTA024.ui.theme.blueButton
import mx.ipn.escom.TTA024.ui.theme.fontMonserrat
import mx.ipn.escom.TTA024.ui.theme.redButton

import mx.ipn.escom.TTA024.ui.viewmodels.AdminEjerciciosViewModel
import mx.ipn.escom.TTA024.ui.viewmodels.ModulosAdminViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminFormEjercicioComposable(navController: NavController,modulo: Modulo ,ejercicio: Ejercicio,adminEjerciciosViewModel: AdminEjerciciosViewModel){
    var editEjercicio=true
    if (ejercicio?.idEjercicio==0 && (ejercicio?.planteamientoEjercicio.equals("") || ejercicio?.tipo.equals("none"))){
        editEjercicio=false
    }
    Log.i("Ejercicio pares", ejercicio.paresCorrectosEjercicio)
    var tiempoEjercicio by remember {
        var titTemp:String=""
        if(editEjercicio){
            titTemp= ejercicio.tiempoEjercicio.toString()
        }
        mutableStateOf(titTemp)
    }

    var incisos = remember {
        mutableStateListOf<String>()
    }

    var listaIncorrectas = remember {
        mutableStateListOf<String>()
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
            if(ejercicio.tipo=="multChoice"){
                tipoEjercicio="Opción multiple"
            }
            if(ejercicio.tipo=="fillBlank"){
                tipoEjercicio="Completar respuesta"
            }
            if(ejercicio.tipo=="relateColumns"){
                tipoEjercicio="Relacionar columnas"
            }
        }
        mutableStateOf(tipoEjercicio)
    }

    var cuerpo by remember {
        var cuerpoString:String=""
        if (editEjercicio){
            cuerpoString=ejercicio.cuerpo
        }
        mutableStateOf(cuerpoString)
    }

    var respCorrecta by remember {
        var respTemp:String=""
        if(tipoEjercicio=="Opción multiple"){
            respTemp=ejercicio.respCorrectaEjercicio
        }
        if(tipoEjercicio=="Completar respuesta"){
            respTemp=ejercicio.respCorrectaEjercicio
        }
        mutableStateOf(respTemp)
    }

    var respIncorrectas by remember {
        var respTemp:String=""
        if(tipoEjercicio=="Opción multiple"){
            respTemp=ejercicio.respIncorrectasEjercicio
        }
        mutableStateOf(respTemp)
    }

    var paresCorrectos by remember {
        var respTemp:String=""
        if(tipoEjercicio=="Relacionar columnas"){
            respTemp=ejercicio.paresCorrectosEjercicio
        }
        mutableStateOf(respTemp)
    }

    val niveles = listOf("Facil","Intermedio","Dificil")
    val tiposEjercicios = listOf("Opción multiple","Relacionar columnas","Completar respuesta")
    var expandedNiveles by remember {
        mutableStateOf(false)
    }
    var expandedTiposEjercicios by remember {
        mutableStateOf(false)
    }


    val isvalidCuerpo = remember(cuerpo){
        !cuerpo.isEmpty()
    }
    var isValidPlanteamiento= remember(planteamiento) {
        !planteamiento.isEmpty()
    }
    var isValidTiempo= remember(tiempoEjercicio) {
        !tiempoEjercicio.isEmpty() || Regex("^[0-9]+\$").matches(tiempoEjercicio)
    }

    val context = LocalContext.current

    var showError by rememberSaveable {
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
                    if(!isvalidCuerpo && !isValidPlanteamiento && !isValidTiempo){
                        showError=true
                    }else{
                        var id=0
                        if(editEjercicio){
                            id=ejercicio.idEjercicio
                        }
                        var ejercicioNuevo: Ejercicio? = null
                        if(nivelEjercicio=="Facil"){
                            nivelEjercicio="1"
                        }
                        if(nivelEjercicio=="Intermedio"){
                            nivelEjercicio="2"
                        }
                        if(nivelEjercicio=="Dificil"){
                            nivelEjercicio="3"
                        }
                        if(tipoEjercicio=="Opción multiple"){
                            respIncorrectas=listaIncorrectas[0]+";"+listaIncorrectas[1]+";"+listaIncorrectas[2]
                            ejercicioNuevo=Ejercicio(id,cuerpo,"multChoice",tiempoEjercicio.toInt(),nivelEjercicio.toInt(),planteamiento,respCorrecta,respIncorrectas,"",1, modulo.idModulo)
                        }
                        if(tipoEjercicio=="Completar respuesta"){
                            ejercicioNuevo=Ejercicio(id,cuerpo,"fillBlank",tiempoEjercicio.toInt(),nivelEjercicio.toInt(),planteamiento,respCorrecta,"","",1, modulo.idModulo)
                        }
                        if(tipoEjercicio=="Relacionar columnas"){
                            paresCorrectos=incisos[0]+":"+incisos[1]+";"+incisos[2]+":"+incisos[3]+";"+incisos[4]+":"+incisos[5]+";"+incisos[6]+":"+incisos[7]
                            ejercicioNuevo=Ejercicio(id,cuerpo,"relateColumns",tiempoEjercicio.toInt(),nivelEjercicio.toInt(),planteamiento,"","",paresCorrectos,1, modulo.idModulo)
                        }

                        if(editEjercicio){
                            adminEjerciciosViewModel.onUpdateEjercicio(ejercicio, ejercicioNuevo!!)
                        }else{
                            adminEjerciciosViewModel.onCreateEjercicio(ejercicioNuevo!!)
                        }
                        navController.popBackStack()
                    }

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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
                        ,
                        isError = !isValidPlanteamiento
                    )

                    OutlinedTextField(
                        value = cuerpo,
                        onValueChange = { cuerpo = it },
                        label = { Text("Cuerpo ejercicio (Integral)") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        isError = !isvalidCuerpo
                    )

                    OutlinedTextField(
                        value = tiempoEjercicio,
                        onValueChange = { tiempoEjercicio = it },
                        label = { Text("Tiempo promedio para resolver (en segundos)") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        isError = !isValidTiempo
                    )

                    ////MENU NIVEL///////
                    ExposedDropdownMenuBox(
                        expanded = expandedNiveles,
                        onExpandedChange = {
                            expandedNiveles = !expandedNiveles
                        }
                    ) {
                        TextField(
                            value = nivelEjercicio,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = "Nivel", color = Color.Black)},
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedNiveles) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedNiveles,
                            onDismissRequest = { expandedNiveles = false }
                        ) {
                            niveles.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        nivelEjercicio = item
                                        expandedNiveles = false
                                        //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    ///////////////////
                    Spacer(modifier = Modifier.height(10.dp))
                    ////MENU TIPO DE EJERCICIO///////
                    ExposedDropdownMenuBox(

                        expanded = expandedTiposEjercicios,
                        onExpandedChange = {
                            expandedTiposEjercicios = !expandedTiposEjercicios
                        },

                    ) {
                        TextField(
                            value = tipoEjercicio,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = "Tipo de ejercicio", color = Color.Black)},
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTiposEjercicios) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(

                            expanded = expandedTiposEjercicios,
                            onDismissRequest = { expandedTiposEjercicios = false },

                        ) {
                            tiposEjercicios.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        tipoEjercicio = item
                                        expandedTiposEjercicios = false
                                        //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    },

                                )
                            }
                        }
                    }
                    //}

                    /////////////RELACIONAR COLUMNAS ///////////////////
                    if (tipoEjercicio == "Relacionar columnas") {
                        if(ejercicio.paresCorrectosEjercicio=="" || ejercicio.paresCorrectosEjercicio=="none"){
                            for(i in 0..7){
                                incisos.add("")
                            }
                        }else{
                            var pares: List<String> = paresCorrectos.split(";")
                            pares.forEach {
                                val preguntaRespuesta = it.split(":")
                                preguntaRespuesta.forEach {
                                    incisos.add(it)
                                }
                            }
                        }

                        for (i in 0..3) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)) {
                                OutlinedTextField(
                                    value = incisos[i * 2],
                                    onValueChange = { incisos[i * 2] = it },
                                    label = { Text("Par " + (i+1)) },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier.width(115.dp)
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                OutlinedTextField(
                                    value = incisos[i * 2 + 1],
                                    onValueChange = { incisos[i * 2 + 1] = it
                                                    Log.i("incisos[]",incisos[i * 2 + 1])
                                                    },
                                    label = { Text("Par" + (i+1)) },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                    modifier = Modifier.width(115.dp)
                                )
                            }
                        }
                    }
                    ////////////////////////////////OPCIÓN MULTIPLE////////////////////
                    if (tipoEjercicio == "Opción multiple") {
                        if((ejercicio.respCorrectaEjercicio=="" || ejercicio.respCorrectaEjercicio=="none") && (ejercicio.respIncorrectasEjercicio=="" || ejercicio.respIncorrectasEjercicio=="none")){
                            for(i in 0..2){
                                listaIncorrectas.add("")
                            }
                        }else{
                            var incorrectas: List<String> = respIncorrectas.split(";")
                            incorrectas.forEach {
                                listaIncorrectas.add(it)
                            }
                        }
                        OutlinedTextField(
                            value = respCorrecta,
                            onValueChange = { respCorrecta = it },
                            label = { Text("Respuesta Correcta") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                        )
                        for(i in 0..2){
                            OutlinedTextField(
                                value = listaIncorrectas[i],
                                onValueChange = { listaIncorrectas[i] = it },
                                label = { Text("Respuesta incorrecta "+(i+1)) },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                            )
                        }
                    }
                    /////////////////////RESPUESTA CORRECTA /////////////////////
                    if (tipoEjercicio == "Completar respuesta") {
                        OutlinedTextField(
                            value = respCorrecta,
                            onValueChange = { respCorrecta = it },
                            label = { Text("Respuesta Correcta") },
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
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
    DialogErrorForm(showError, { showError = false }, { showError = false })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogErrorForm(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            modifier = Modifier
                .height(306.dp)
                .width(351.dp)
                .clip(RoundedCornerShape(28.dp)),
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                Text(
                    text = "Por favor, revisa los campos",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = fontMonserrat,
                    textAlign = TextAlign.Center,

                )
                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = {
                        onConfirm()
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
                        text = "Aceptar",
                        fontFamily = fontMonserrat,
                        color = redButton,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}