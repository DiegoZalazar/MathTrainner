package mx.ipn.escom.tta047.ui.estudianteUI.exercises.fillblank

data class FillBlank(
    val instrucciones: String,
    val latex: String,
    val respuesta: String,
    val tiempo: Int = 0,
    val modulo: Int = 0
)
