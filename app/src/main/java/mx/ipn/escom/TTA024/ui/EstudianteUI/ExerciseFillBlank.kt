package mx.ipn.escom.TTA024.ui.EstudianteUI

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

@Composable
fun ExerciseFillBlank(
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
                    text = "Completa el cambio de variable en el siguiente procedimiento: ",
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
                    Image(
                        painter = painterResource(id = R.drawable.latex3),
                        contentDescription = "latex example 2",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(200.dp)
                    )
                }

            }

            Button(
                onClick = {},
                modifier = modifier
                    .widthIn(min = 250.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Comprobar")
            }
            FillBlankComponent()
        }
    }
}

@Composable
fun FillBlankComponent(
    modifier: Modifier = Modifier
) {
    var resp by remember { mutableStateOf("") }
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton("7", { resp += "7" })
            KeyButton("8", { resp += "8" })
            KeyButton("9", { resp += "9" })
            KeyButton("/", { resp += "/" })
            KeyButton("*", { resp += "*" })
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton("4", { resp += "4" })
            KeyButton("5", { resp += "5" })
            KeyButton("6", { resp += "6" })
            KeyButton("√", { resp += "√" })
            KeyButton("-", { resp += "-" })
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton("1", { resp += "1" })
            KeyButton("2", { resp += "2" })
            KeyButton("3", { resp += "3" })
            KeyButton("^", { resp += "^" })
            KeyButton("+", { resp += "*" })
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyButton(".", { resp += "." })
            KeyButton("0", { resp += "0" })
            KeyButton("x", { resp += "x" })
            KeyButton("dx", { resp += "dx" })
            KeyButton("←", { resp = resp.dropLast(1) })
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