package mx.ipn.escom.tta047.ui.estudianteUI.exercises.fillblank

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import mx.ipn.escom.tta047.ui.smallcomponents.LaTeXView
import mx.ipn.escom.tta047.ui.smallcomponents.ModalBottomSheetButton
import mx.ipn.escom.tta047.ui.smallcomponents.ModalBottomSheetMessage
import mx.ipn.escom.tta047.ui.theme.MathTrainerTheme

@Composable
fun ExerciseFillBlank(
    exerciseFillBlankViewModel: ExerciseFillBlankViewModel = viewModel(),
    nextAction: (Boolean) -> Unit = {},
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var fillBlankUIState = exerciseFillBlankViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .verticalScroll(rememberScrollState())
            ){
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
                modifier = Modifier
                    .widthIn(min = 250.dp)
                    .padding(vertical = 16.dp),
                enabled = !exerciseFillBlankViewModel.resuelto
            ) {
                Text("Comprobar")
            }

            Column(
                modifier = Modifier.wrapContentHeight()
            ){
                FillBlankComponent(exerciseFillBlankViewModel.respuesta, exerciseFillBlankViewModel::addToRespuesta, exerciseFillBlankViewModel::dropFromRespuesta)
            }

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
                            message = if(exerciseFillBlankViewModel.correcto) "¡Muy bien!" else "Incorrecto"
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            if(!exerciseFillBlankViewModel.correcto){
                                ModalBottomSheetButton(
                                    onClick = {
                                        onRetry()
                                        exerciseFillBlankViewModel.reset()
                                    },
                                    correct = exerciseFillBlankViewModel.correcto,
                                    message = "Reintentar"
                                )
                            }
                            ModalBottomSheetButton(
                                onClick = { nextAction(exerciseFillBlankViewModel.correcto) },
                                correct = exerciseFillBlankViewModel.correcto,
                                message = "Siguiente"
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(32.dp)
                        )
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
    val keys = listOf(
        "7",
        "8",
        "9",
        "(",
        ")",
        "4",
        "5",
        "6",
        "+",
        "-",
        "1",
        "2",
        "3",
        "*",
        "/",
        "y",
        "0",
        "x",
        "^"
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = resp, onValueChange = {}, readOnly=true, label = {Text("Tu respuesta:")})
        }
        Spacer(Modifier.height(12.dp))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(5),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(keys) { key ->
                KeyButton(key = key, onClic = { add(key) })
            }
            item {
                KeyButton(key = "←", onClic = { drop() })
            }
        }
    }
}

@Composable
fun KeyButton(
    key: String = "",
    onClic: () -> Unit,
    modifier: Modifier = Modifier
) {
    val size = if(key=="dx") 18.sp else 24.sp
    FilledTonalButton(
        onClick = {onClic()},
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface, RoundedCornerShape(10)),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.filledTonalButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurface)
    ) {
        Text(
            text = key,
            fontSize = size,
            maxLines = 1,
            softWrap = true
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
