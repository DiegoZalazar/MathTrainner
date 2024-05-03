package mx.ipn.escom.TTA024.ui.smallcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ModalBottomSheetMessage(
    correct: Boolean = false,
    message: String = "",
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = if(correct) "Correcto" else "Incorrecto",
            style = MaterialTheme.typography.headlineMedium,
            color = if(correct) Color(0xFF209401) else MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            color = if(correct) Color(0xFF209401) else MaterialTheme.colorScheme.error
        )
    }
}