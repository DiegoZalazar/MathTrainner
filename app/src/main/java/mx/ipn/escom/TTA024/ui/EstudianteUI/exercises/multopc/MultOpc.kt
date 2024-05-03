package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc

data class MultOpc(
    val instrucciones: String,
    val cuerpo: String,
    val respCorrecta: String,
    val opciones: List<String>,
    val tiempo: Int = 0
)
