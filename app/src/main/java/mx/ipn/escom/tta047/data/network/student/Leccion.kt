package mx.ipn.escom.tta047.data.network.student

import kotlinx.serialization.Serializable

@Serializable
data class Leccion(
    val descripcionLeccion: String,
    val idLeccion: Int,
    val idModulo: Int,
    val nivelLeccion: Int,
    val recursoMultimedia: String,
    val tituloLeccion: String
)