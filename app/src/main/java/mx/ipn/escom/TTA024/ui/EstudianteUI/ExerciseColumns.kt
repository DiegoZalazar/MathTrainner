package mx.ipn.escom.TTA024.ui.EstudianteUI

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ExerciseColumns(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row( // Barra superior
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.10f)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.15f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                LinearProgressIndicator(
                    progress = 0.5f ,
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
        } // /Barra superior
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
            Columns(modifier = Modifier.fillMaxHeight(0.7f))
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
    modifier: Modifier = Modifier
) {
    val prctWidth = 0.8f

    // Calculate width and height for each OptionButton
    val buttonWidth = 100.dp
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
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.WRONG)
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth))
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth))
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.ACTIVE)
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.RIGHT)
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth))
            OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth))
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