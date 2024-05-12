package mx.ipn.escom.TTA024.data.network.student

import kotlinx.serialization.Serializable

@Serializable
data class Modulo (
    val idModulo: Int,
    val nombreModulo: String,
    val tema: String,
    val avance: Float
)