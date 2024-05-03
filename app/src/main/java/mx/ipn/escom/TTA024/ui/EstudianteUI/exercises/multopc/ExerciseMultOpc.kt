package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.TTA024.ui.smallcomponents.ButtonState
import mx.ipn.escom.TTA024.ui.smallcomponents.LaTeXView
import mx.ipn.escom.TTA024.ui.smallcomponents.ModalBottomSheetButton
import mx.ipn.escom.TTA024.ui.smallcomponents.ModalBottomSheetMessage
import mx.ipn.escom.TTA024.ui.smallcomponents.OptionButton
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ExerciseMultOpc(
    exerciseMultOpcViewModel: ExerciseMultOpcViewModel = viewModel(),
    nextAction: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val multOpcUIState by exerciseMultOpcViewModel.uiState.collectAsState()

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
                    text = multOpcUIState.instrucciones,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(15)),
                    contentAlignment = Alignment.Center
                ){
//                    Image(
//                        painter = painterResource(id = R.drawable.latex2),
//                        contentDescription = "latex example 2",
//                        contentScale = ContentScale.Fit,
//                        modifier = Modifier.size(150.dp)
//                    )
                    Box(modifier = Modifier.padding(24.dp)){
                        LaTeXView(_latex = multOpcUIState.cuerpo)
                    }

                }

            }

            Spacer(modifier = Modifier.height(12.dp))
            Options(
                onClick = { exerciseMultOpcViewModel.changeSelection(it) },
                opciones = multOpcUIState.opciones,
                estados = exerciseMultOpcViewModel.buttonStates,
                modifier = Modifier.fillMaxHeight(0.7f))
            Button(
                onClick = { exerciseMultOpcViewModel.checkAnswer() },
                modifier = modifier
                    .widthIn(min = 250.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Comprobar")
            }
        }
        val sheetState = rememberModalBottomSheetState()
        if(multOpcUIState.correcto || multOpcUIState.error){
//        Dialog(
//            onClick = {exerciseMultOpcViewModel.reset()},
//            correct = multOpcUIState.correcto
//        )

            ModalBottomSheet(
                onDismissRequest = { exerciseMultOpcViewModel.reset() },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    ModalBottomSheetMessage(
                        correct = multOpcUIState.correcto,
                        message = if(multOpcUIState.correcto) "¡Muy bien!" else "Respuesta correcta: ${multOpcUIState.respCorrecta}"
                    )
                    ModalBottomSheetButton(
                        onClick = { nextAction(multOpcUIState.correcto) },
                        correct = multOpcUIState.correcto,
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

@Composable
fun Options(
    onClick: (Int) -> Unit,
    opciones: List<String>,
    estados: List<ButtonState>,
    modifier: Modifier = Modifier
){
    val prctWidth = 1f
    val buttonWidth = 180.dp
    val buttonHeight = 50.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OptionButton(latex = opciones[0], onClick = { onClick(0) }, width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = estados[0])
        OptionButton(latex = opciones[1], onClick = { onClick(1) }, width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = estados[1])
        OptionButton(latex = opciones[2], onClick = { onClick(2) }, width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = estados[2])
        OptionButton(latex = opciones[3], onClick = { onClick(3) }, width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = estados[3])
    }
}

@Composable
fun Dialog(
    onClick: () -> Unit,
    correct: Boolean
){
    val title = if(correct) "Correcto" else "Incorrecto"
    AlertDialog(
        onDismissRequest = {
            onClick()
        },
        confirmButton = {
            TextButton(
                onClick= onClick,

                ){
                Text("Otra vez")
            }
        },
        title = { Text(text = title) }
    )
}




@Preview(showBackground = true, device="id:pixel_5")
@Composable
fun ExerciseMultOpcPreview(){
    MathTrainerTheme {
        ExerciseMultOpc()
    }
}