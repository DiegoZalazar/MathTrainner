package mx.ipn.escom.tta047.ui.estudianteUI.exercises.multopc

data class MultOpc(
    val instrucciones: String,
    val cuerpo: String,
    val respCorrecta: String,
    val opciones: List<String>,
    val tiempo: Int = 0
)
