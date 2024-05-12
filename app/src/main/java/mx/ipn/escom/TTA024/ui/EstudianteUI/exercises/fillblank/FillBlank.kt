package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.fillblank

data class FillBlank(
    val instrucciones: String,
    val latex: String,
    val respuesta: String,
    val tiempo: Int = 0
)
