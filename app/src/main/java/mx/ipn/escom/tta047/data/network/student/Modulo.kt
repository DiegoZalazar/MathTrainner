package mx.ipn.escom.tta047.data.network.student

import kotlinx.serialization.Serializable

@Serializable
data class Modulo (
    val idModulo: Int,
    val nombreModulo: String,
    val tema: String,
    val avanceModulo: Float
)