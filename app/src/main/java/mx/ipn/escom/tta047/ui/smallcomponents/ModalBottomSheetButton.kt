package mx.ipn.escom.tta047.ui.smallcomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModalBottomSheetButton(
    onClick: () -> Unit = {},
    correct: Boolean = false,
    message: String = ""
){
    Button(
        onClick = { onClick() },
        modifier = Modifier.padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(correct) Color(0xFF209401) else MaterialTheme.colorScheme.errorContainer,
            contentColor = if(correct) Color(0xFF0C3900) else MaterialTheme.colorScheme.error
        )
    ) {
        Text(message)
    }
}