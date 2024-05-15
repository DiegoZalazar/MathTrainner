package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.columns.ExerciseColumns
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.columns.ExerciseColumnsViewModel
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.fillblank.ExerciseFillBlank
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.fillblank.ExerciseFillBlankViewModel
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc.ExerciseMultOpc
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc.ExerciseMultOpcViewModel
import mx.ipn.escom.TTA024.ui.smallcomponents.TimeText

@Composable
fun ExercisesScreen(
    exercisesUIState: ExercisesUIState,
    nextAction: (Boolean, Int) -> Unit = {a,b -> },
    cancelAction: () -> Unit = {}
){
    val animatedProgress by animateFloatAsState(
        targetValue = exercisesUIState.progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    var alert by remember { mutableStateOf(false) }
    Log.i("Timer", "Is it running: ${exercisesUIState.running}")
    var seconds by rememberSaveable { mutableIntStateOf(0) }

    if(exercisesUIState.running){
        LaunchedEffect(key1 = exercisesUIState) {
            coroutineScope {
                launch {
                    while(exercisesUIState.running){
                        delay(1000L)
                        seconds += 1
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Row( // Barra superior
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.10f)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = {alert = true}) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.7f, true)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ){
                    LinearProgressIndicator(
                        progress = animatedProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 12.dp, height = 12.dp)
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(50)
                            ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    TimeText(timeInSeconds = seconds)
                }
            } // /Barra superior
        }
    ) {innerPadding ->
        if(exercisesUIState.exercises.isEmpty()){
            NoExercicesScreen(
                cancelAction = cancelAction
            )
        } else {
            when(val exerciseUIState = exercisesUIState.currExerciseUIState){
                is ExerciseUIState.ExerciseUIStateColumns -> ExerciseColumns(
                    nextAction = { nextAction(it, seconds) },
                    exerciseColumnsViewModel = ExerciseColumnsViewModel(exerciseUIState.exerciseColumns),
                    modifier = Modifier.padding(innerPadding)
                )
                is ExerciseUIState.ExerciseUIStateFillBlank -> ExerciseFillBlank(
                    nextAction = { nextAction(it, seconds)  },
                    exerciseFillBlankViewModel = ExerciseFillBlankViewModel(exerciseUIState.exerciseFillBlank),
                    modifier = Modifier.padding(innerPadding)
                )
                is ExerciseUIState.ExerciseUIStateMultOpc -> ExerciseMultOpc(
                    nextAction = { nextAction(it, seconds)  },
                    exerciseMultOpcViewModel = ExerciseMultOpcViewModel(exerciseUIState.exerciseOptions),
                    modifier = Modifier.padding(innerPadding)
                )
                is ExerciseUIState.FinishedExercises -> FinishedExercisesScreen(
                    correctExercises = exercisesUIState.correctExercises,
                    totalExercises = exercisesUIState.exercises.size,
                    finishAction = cancelAction,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
    if(alert){
        AlertDialog(
            title = {
                Text(text = "Cancelar Ejercicios")
            },
            text = {
                Text(text = "¿Estas seguro que deseas cancelar esta sesion? Perderas todo tu progreso")
            },
            onDismissRequest = {
                alert = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        alert = false
                        cancelAction()
                    }
                ) {
                    Text("Si")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        alert = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    BackHandler(enabled = true) {
        alert = true
    }
}

@Composable
fun NoExercicesScreen(
    cancelAction: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Ups, aun no hay ejercicios",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = cancelAction
        ) {
            Text("Regresar")
        }
    }
}

@Composable
fun FinishedExercisesScreen(
    correctExercises: Int,
    totalExercises: Int,
    finishAction: () -> Unit = {},
    modifier: Modifier = Modifier
){
    var correctPercentage by remember { mutableStateOf(0.0f) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            text = "¡Felicidades!\nCompletaste los ejercicios",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(R.drawable.fireworks),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
        Text(text = "Respuestas correctas:", fontSize = 24.sp)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ){
            correctPercentage = correctExercises.toFloat()/totalExercises
            val animatedProgress by animateFloatAsState(
                targetValue = correctPercentage,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )
            CircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier.size(100.dp)
            )

            Text(text = "${correctExercises} / ${totalExercises}",
                fontSize = 24.sp
            )
        }
        Button(
            onClick = finishAction
        ){
            Text("Regresar a inicio")
        }
    }
}