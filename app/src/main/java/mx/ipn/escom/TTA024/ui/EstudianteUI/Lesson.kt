package mx.ipn.escom.TTA024.ui.EstudianteUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.data.network.student.Leccion
import mx.ipn.escom.TTA024.ui.EstudianteUI.home.LeccionUIState
import mx.ipn.escom.TTA024.ui.EstudianteUI.home.StudentHomeViewModel
import mx.ipn.escom.TTA024.ui.smallcomponents.YoutubePlayer
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun Lesson (
    studentHomeViewModel: StudentHomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
){
    var leccionUIState = studentHomeViewModel.leccionUIState
    when(leccionUIState) {
        is LeccionUIState.Error -> LessonError(regresar = {navController.navigateUp()})
        is LeccionUIState.Loading -> CircularProgressIndicator()
        is LeccionUIState.NoContent -> LessonNoContent(regresar = {navController.navigateUp()})
        is LeccionUIState.Success -> LessonSuccess(
            navController = navController,
            leccion = leccionUIState.leccion
        )
    }
}

@Composable
fun LessonError(
    modifier: Modifier = Modifier,
    regresar: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Error al obtener la leccion")
        Button(onClick = regresar) {
            Text("Regresar")

        }
    }
}

@Composable
fun LessonSuccess(
    leccion: Leccion,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Text(
                            text = leccion.tituloLeccion,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                },
                modifier = modifier,
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        },
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = it,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                YoutubePlayer(
                    youtubeVideo = leccion.recursoMultimedia,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
            }

            item {
                Text(text = leccion.descripcionLeccion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp))

            }
        }
    }
}

@Composable
fun LessonNoContent(
    modifier: Modifier = Modifier,
    regresar: () -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Aun no hay lecciones para este modulo")
        Button(onClick = regresar) {
            Text("Regresar")
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun LessonPreview(){
    MathTrainerTheme {
        Lesson(navController = rememberNavController(), studentHomeViewModel = viewModel())
    }
}