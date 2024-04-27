package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc

data class MultOpcUIState(
    val instrucciones: String,
    val cuerpo: String,
    val respCorrecta: String,
    val opciones: List<String>,
    val error: Boolean,
    val correcto: Boolean
)