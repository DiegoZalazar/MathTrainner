package mx.ipn.escom.tta047.ui.estudianteUI.exam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.tta047.ui.estudianteUI.exercises.ExercisesScreenViewModel
import mx.ipn.escom.tta047.ui.estudianteUI.home.StudentHomeViewModel

@Composable
fun ExamInfoScreen(
    studentVM: StudentHomeViewModel,
    exercisesScreenViewModel: ExercisesScreenViewModel,
    onSkipExam: () -> Unit,
    onStartExam: () -> Unit,
    examDone: Boolean
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Examen Diagnóstico.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))
        if(!examDone){
            Text("Hemos detectado que aún no has hecho el examen.")
            Spacer(Modifier.height(24.dp))
        }
        Icon(imageVector = Icons.Default.ChecklistRtl, "checklist", modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(24.dp))
        Text(
            text = "¡Prepárate para el desafío! Este examen diagnóstico nos ayudará a conocer tus fortalezas y áreas de mejora en matemáticas. ¡Buena suerte!",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(64.dp))
        Button(onClick = onStartExam){
            Text("Realizar Examen")
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = onSkipExam) {
            Text("Omitir")
        }
    }
}