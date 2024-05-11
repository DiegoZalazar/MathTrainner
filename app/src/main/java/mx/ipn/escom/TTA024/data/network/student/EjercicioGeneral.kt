package mx.ipn.escom.TTA024.data.network.student

data class EjercicioGeneral(
    val idEjercicio: Int,
    val idModulo: Int,
    val nivelEjercicio: Int,
    val tiempoEjercicio: Int,
    val tipoEjercicio: String,
    val planteamientoEjercicio: String,
    val cuerpo: String?,
    val paresCorrectos: String?,
    val respCorrectaEjercicio: String?,
    val respIncorrectasEjercicio: String?
)