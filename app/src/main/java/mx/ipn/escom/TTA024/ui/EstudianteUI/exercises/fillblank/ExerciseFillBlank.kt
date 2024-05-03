package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.fillblank

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.TTA024.ui.smallcomponents.LaTeXView
import mx.ipn.escom.TTA024.ui.smallcomponents.ModalBottomSheetButton
import mx.ipn.escom.TTA024.ui.smallcomponents.ModalBottomSheetMessage
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ExerciseFillBlank(
    exerciseFillBlankViewModel: ExerciseFillBlankViewModel = viewModel(),
    nextAction: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var fillBlankUIState = exerciseFillBlankViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = fillBlankUIState.value.instrucciones,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(10)),
                    contentAlignment = Alignment.Center
                ){
                    Box(modifier = Modifier.padding(24.dp)){
                        LaTeXView(_latex = fillBlankUIState.value.latex)
                    }
                }

            }

            Button(
                onClick = { exerciseFillBlankViewModel.comprobar() },
                modifier = modifier
                    .widthIn(min = 250.dp)
                    .padding(vertical = 8.dp),
                enabled = !exerciseFillBlankViewModel.resuelto
            ) {
                Text("Comprobar")
            }

            FillBlankComponent(exerciseFillBlankViewModel.respuesta, exerciseFillBlankViewModel::addToRespuesta, exerciseFillBlankViewModel::dropFromRespuesta)

            val sheetState = rememberModalBottomSheetState()
            if(exerciseFillBlankViewModel.resuelto){
                ModalBottomSheet(
                    onDismissRequest = { exerciseFillBlankViewModel.reset() },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        ModalBottomSheetMessage(
                            correct = exerciseFillBlankViewModel.correcto,
                            message = if(exerciseFillBlankViewModel.correcto) "¡Muy bien!" else "Respuesta correcta: ${fillBlankUIState.value.respuesta}"
                        )
                        ModalBottomSheetButton(
                            onClick = { nextAction(exerciseFillBlankViewModel.correcto) },
                            correct = exerciseFillBlankViewModel.correcto,
                            message = "Siguiente"
                        )
                        Spacer(
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FillBlankComponent(
    resp: String,
    add: (String) -> Unit,
    drop: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "u = ",
                fontSize = 24.sp
            )
            Text(
                text = resp,
                fontSize = 24.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton("7", { add("7") })
            KeyButton("8", { add("8") })
            KeyButton("9", { add("9") })
            KeyButton("/", { add("/") })
            KeyButton("*", { add("*") })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton("4", { add("4") })
            KeyButton("5", { add("5") })
            KeyButton("6", { add("6") })
            KeyButton("√", { add("√") })
            KeyButton("-", { add("-") })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton("1", { add("1") })
            KeyButton("2", { add("2") })
            KeyButton("3", { add("3") })
            KeyButton("^", { add("^") })
            KeyButton("+", { add("+") })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton(".", { add(".") })
            KeyButton("0", { add("0") })
            KeyButton("x", { add("x") })
            KeyButton("dx", { add("dx") })
            KeyButton("←", { drop() })
        }
    }
}

@Composable
fun KeyButton(
    key: String = "",
    onClic: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = {onClic()},
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface, RoundedCornerShape(10)),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.filledTonalButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurface)
    ) {
        Text(
            text = key,
            fontSize = 24.sp
        )
    }
}


@Preview(showBackground = true, device="id:pixel_5")
@Composable
fun ExerciseFillBlankPreview(){
    MathTrainerTheme {
        ExerciseFillBlank()
    }
}
