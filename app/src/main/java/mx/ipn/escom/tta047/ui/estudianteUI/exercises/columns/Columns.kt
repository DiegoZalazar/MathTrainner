package mx.ipn.escom.tta047.ui.estudianteUI.exercises.columns

data class Columns(
    val instrucciones: String,
    val pares: List<Pair<String, String>>,
    val tiempo: Int = 0
)
