package mx.ipn.escom.tta047.ui.estudianteUI.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.ipn.escom.tta047.R
import mx.ipn.escom.tta047.ui.estudianteUI.home.EjerciciosUIState
import mx.ipn.escom.tta047.ui.estudianteUI.home.StudentHomeViewModel

@Composable
fun ExercisesStartScreen(
    studentVM: StudentHomeViewModel,
    regresar: () -> Unit = {},
    navToExercises: () -> Unit = {}
){
    val exercisesUIState = studentVM.ejerciciosUIState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when(exercisesUIState) {
            is EjerciciosUIState.Error -> ExercisesStartScreenError(regresar = regresar)
            is EjerciciosUIState.Loading -> CircularProgressIndicator()
            is EjerciciosUIState.NoContent -> ExercisesStartScreenNoContent(regresar = regresar)
            is EjerciciosUIState.Success -> ExercisesStartScreenSuccess(
                regresar = regresar,
                navToExercises = navToExercises,
                nombreModulo = exercisesUIState.nombreModulo,
                examDone = studentVM.examenDone
            )
        }
    }
}

@Composable
fun ExercisesStartScreenSuccess(
    modifier: Modifier = Modifier,
    regresar: () -> Unit = {},
    navToExercises: () -> Unit = {},
    nombreModulo: String = "ejercicios calculo",
    examDone: Boolean
) {
    var circularBarProgress by remember { mutableStateOf(0.0f) }
    var loadProgress by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = loadProgress) {
        while(loadProgress){
            delay(10)
            circularBarProgress += 0.01f
            if(circularBarProgress > 1.0f){
                loadProgress = false
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = nombreModulo,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Realiza cada ejercicio correctamente y lo más rápido que puedas para avanzar en el módulo:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(100.dp),
            contentAlignment = Alignment.Center,
        ){
            CircularProgressIndicator(
                progress = { circularBarProgress },
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.modulo),
                contentDescription = "modulo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(9.dp)
                    .fillMaxSize()
            )
        }
        Spacer(Modifier.height(16.dp))
        if(examDone){
            Text(
                text = "Recuerda revisar la lección antes de hacer los ejercicios.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(32.dp))
        OutlinedButton(onClick = regresar) {
            Text("Regresar")
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = navToExercises
        ){
            Text("Iniciar ejercicios")
        }
    }
}


@Composable
fun ExercisesStartScreenNoContent(
    modifier: Modifier = Modifier,
    regresar: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ups, aun no hay ejercicios",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = regresar
        ) {
            Text("Regresar")
        }
    }
}

@Composable
fun ExercisesStartScreenError(
    modifier: Modifier = Modifier,
    regresar: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ocurrio un error inesperado",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = regresar
        ) {
            Text("Regresar")
        }
    }
}