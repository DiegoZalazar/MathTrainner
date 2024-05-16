package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.columns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.TTA024.ui.smallcomponents.ModalBottomSheetButton
import mx.ipn.escom.TTA024.ui.smallcomponents.ModalBottomSheetMessage
import mx.ipn.escom.TTA024.ui.smallcomponents.OptionButton
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ExerciseColumns(
    exerciseColumnsViewModel: ExerciseColumnsViewModel = viewModel(),
    nextAction: (Boolean) -> Unit = {},
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                .fillMaxHeight()
        ){
            InstructionsText(
                text = "Relaciona las integrales con su resultado:",
                modifier = Modifier
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Columns(
                opcionesDerecha = exerciseColumnsViewModel.opcionesDerecha,
                opcionesIzquierda = exerciseColumnsViewModel.opcionesIzquierda,
                seleccionar = exerciseColumnsViewModel::seleccionar,
                modifier = Modifier.fillMaxHeight(0.7f)
            )
            // bottom sheet
            val sheetState = rememberModalBottomSheetState()
            if(exerciseColumnsViewModel.incorrecto || exerciseColumnsViewModel.correcto){
//        Dialog(
//            onClick = {exerciseMultOpcViewModel.reset()},
//            correct = multOpcUIState.correcto
//        )

                ModalBottomSheet(
                    onDismissRequest = { nextAction(exerciseColumnsViewModel.correcto) },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        ModalBottomSheetMessage(correct = exerciseColumnsViewModel.correcto)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            if(exerciseColumnsViewModel.correcto){
                                ModalBottomSheetButton(
                                    onClick = { nextAction(exerciseColumnsViewModel.correcto) },
                                    correct = exerciseColumnsViewModel.correcto,
                                    message = "Siguiente"
                                )
                            }else{
                                ModalBottomSheetButton(
                                    onClick = {
                                        onRetry()
                                        exerciseColumnsViewModel.reset()
                                    },
                                    correct = exerciseColumnsViewModel.correcto,
                                    message = "Reintentar"
                                )
                                ModalBottomSheetButton(
                                    onClick = { nextAction(exerciseColumnsViewModel.correcto) },
                                    correct = exerciseColumnsViewModel.correcto,
                                    message = "Siguiente"
                                )
                            }
                        }
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
fun InstructionsText (
    modifier: Modifier = Modifier,
    text: String = ""
){
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier
    )
}

@Composable
fun Columns(
    opcionesDerecha: List<Opcion>,
    opcionesIzquierda: List<Opcion>,
    seleccionar: (Boolean,String) -> Unit,
    modifier: Modifier = Modifier
) {
    val prctWidth = 1f

    // Calculate width and height for each OptionButton
    val buttonWidth = 300.dp
    val buttonHeight = 60.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            for(opcion in opcionesDerecha){
                OptionButton(latex = opcion.valor, width = buttonWidth, height = buttonHeight, onClick = { seleccionar(true,opcion.valor) }, modifier = Modifier.fillMaxWidth(prctWidth), state = opcion.estado)
            }
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.WRONG)
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth))
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth))
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            for(opcion in opcionesIzquierda){
                OptionButton(latex = opcion.valor, width = buttonWidth, height = buttonHeight, onClick = { seleccionar(false,opcion.valor) }, modifier = Modifier.fillMaxWidth(prctWidth), state = opcion.estado)
            }
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.ACTIVE)
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.RIGHT)
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth))
//            OptionButton(width = buttonWidth, height = buttonHeight, onClick = {}, modifier = Modifier.fillMaxWidth(prctWidth))
        }
    }
}


@Preview(showBackground = true, device="id:pixel_5")
@Composable
fun ExerciseColumnsPreview(){
    MathTrainerTheme {
        ExerciseColumns()
    }
}