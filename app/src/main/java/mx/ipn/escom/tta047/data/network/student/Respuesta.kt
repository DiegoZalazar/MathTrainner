package mx.ipn.escom.tta047.data.network.student

data class Respuesta(
    val correcta: Boolean,
    val intentos: String,
    val modulo: String,
    val tiempo: String,
    val tipo: String
)