package mx.ipn.escom.tta047.ui.estudianteUI.exercises

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class ExerciseNavScreens(val title: String){
    StartExercises("Start"),
    Exercises("Session Of Exercises")
}

@Composable
fun ExampleNavExercises (
    navController: NavHostController = rememberNavController() // Host Controller and initialization 2)
) {
    val backStackEntry by navController.currentBackStackEntryAsState() // Host Controller and initialization 2)
    val currentScreen = ExerciseNavScreens.valueOf(
        backStackEntry?.destination?.route ?: ExerciseNavScreens.StartExercises.name
    )
    val viewModel : ExercisesScreenViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = ExerciseNavScreens.StartExercises.name
        ){
            composable(route = ExerciseNavScreens.StartExercises.name){
                StarExercisesScreen(navigateToExercises = {navController.navigate(ExerciseNavScreens.Exercises.name)})
            }

            composable(route = ExerciseNavScreens.Exercises.name){
                ExercisesScreen(
                    exercisesUIState = uiState,
                    nextAction = viewModel::nextExercise,
                    cancelAction = {
                        viewModel.reset()
                        navController.popBackStack(ExerciseNavScreens.StartExercises.name, inclusive = false)
                    })
            }
        }
    }
}

@Composable
fun StarExercisesScreen(
    navigateToExercises: ()->Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = navigateToExercises
        ){
            Text("Iniciar Ejercicios")
        }
    }
}