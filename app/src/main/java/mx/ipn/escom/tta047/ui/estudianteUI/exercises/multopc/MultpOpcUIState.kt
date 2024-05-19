package mx.ipn.escom.tta047.ui.estudianteUI.exercises.multopc

data class MultOpcUIState(
    val instrucciones: String,
    val cuerpo: String,
    val respCorrecta: String,
    val opciones: List<String>,
    val error: Boolean,
    val correcto: Boolean
)