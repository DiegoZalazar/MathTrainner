package mx.ipn.escom.TTA024.ui.smallcomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimeText(
    timeInSeconds: Int,
    modifier: Modifier = Modifier
) {
    fun segundosAMinutosYSegundos(segundos: Int): String {
        val minutos = segundos / 60
        val segundosRestantes = segundos % 60
        return String.format("%02d:%02d", minutos, segundosRestantes)
    }

    Text(
        segundosAMinutosYSegundos(timeInSeconds),
        modifier = modifier
    )
}