package mx.ipn.escom.TTA024.ui.estudiante

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mx.ipn.escom.TTA024.R
import mx.ipn.escom.TTA024.ui.theme.MathTrainerTheme

enum class ButtonState {
    ENABLED,
    ACTIVE,
    RIGHT,
    WRONG
}

@Composable
fun OptionButton (
    width : Dp = 0.dp,
    height : Dp = 0.dp,
    state: ButtonState = ButtonState.ENABLED, // Define ButtonState enum
    modifier: Modifier = Modifier
) {
    val borderColor = when (state) {
        ButtonState.ENABLED -> Color.Gray
        ButtonState.ACTIVE -> MaterialTheme.colorScheme.primary // Change to desired color
        ButtonState.RIGHT -> Color.Green // Change to desired color
        ButtonState.WRONG -> Color.Red // Change to desired color
    }

    val backgroundColor = when (state) {
        ButtonState.ENABLED -> Color.LightGray
        ButtonState.ACTIVE -> MaterialTheme.colorScheme.primaryContainer // Change to desired color
        ButtonState.RIGHT -> Color(0xFFCDDCBD) // Change to desired color
        ButtonState.WRONG -> Color(0xFFF4D5D5) // Change to desired color
    }

    Box(
        modifier = modifier
            .size(width = width, height = height)
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .border(
                BorderStroke(2.dp, borderColor),
                shape = RoundedCornerShape(50)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.latex1), /*TODO: CAMBIAR ESTO*/
            contentDescription = "latex example",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OptionButtonPreview(){
    MathTrainerTheme{
        OptionButton(100.dp, 50.dp, ButtonState.ACTIVE)
    }
}