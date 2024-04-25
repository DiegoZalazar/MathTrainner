package mx.ipn.escom.TTA024.ui.EstudianteUI

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ExerciseMultOpc(
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
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Selecciona los valores correctos de u y du para hacer un cambio de variable en la siguiente integral:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Box(
                    modifier = Modifier.fillMaxWidth().padding(horizontal=8.dp).background(Color.LightGray, shape = RoundedCornerShape(15)),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.latex2),
                        contentDescription = "latex example 2",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(150.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(12.dp))
            Options(modifier = Modifier.fillMaxHeight(0.7f))
            Button(
                onClick = {},
                modifier = modifier
                    .widthIn(min = 250.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Comprobar")
            }
        }
    }
}

@Composable
fun Options(
    modifier: Modifier = Modifier
){
    val prctWidth = 0.8f
    val buttonWidth = 100.dp
    val buttonHeight = 50.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.WRONG)
        OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.RIGHT)
        OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth), state = ButtonState.ACTIVE)
        OptionButton(width = buttonWidth, height = buttonHeight, modifier = Modifier.fillMaxWidth(prctWidth))
    }
}

@Preview(showBackground = true, device="id:pixel_5")
@Composable
fun ExerciseMultOpcPreview(){
    MathTrainerTheme {
        ExerciseMultOpc()
    }
}