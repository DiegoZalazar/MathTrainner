package mx.ipn.escom.TTA024.Admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.models.Estudiante
import mx.ipn.escom.TTA024.navigation.AppScreens
import mx.ipn.escom.TTA024.ui.theme.blueButton
import mx.ipn.escom.TTA024.ui.theme.fontMonserrat
import mx.ipn.escom.TTA024.ui.theme.greenButton
import mx.ipn.escom.TTA024.ui.theme.redButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogConfirmarAccion(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    texto: String,
    navController: NavController
) {
    val textoModifier = Modifier.padding(top = 5.dp)
    if (show) {
        AlertDialog(

            modifier = Modifier
                .height(400.dp)
                .width(351.dp)
                .clip(RoundedCornerShape(28.dp)),
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Column(
                    modifier = Modifier
                        .height(68.dp)
                        .width(314.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = texto,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = fontMonserrat,
                        textAlign = TextAlign.Center
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.successicon),
                    contentDescription = texto,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .size(165.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)

                )
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = { onDismiss()
                                navController.popBackStack()
                              },
                    modifier = Modifier
                        .width(300.dp)
                        .height(58.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(greenButton, RoundedCornerShape(30.dp)),

                    ) {
                    Text(
                        text = "Aceptar",
                        color = Color.Black,
                        fontFamily = fontMonserrat,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}