package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.columns

data class Columns(
    val instrucciones: String,
    val pares: List<Pair<String, String>>,
    val tiempo: Int = 0
)
