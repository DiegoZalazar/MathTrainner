package mx.ipn.escom.TTA024.data.network.student

import kotlinx.serialization.Serializable

@Serializable
data class Modulo (
    val idModulo: String,
    val nombreModulo: String,
    val idTema: Int?,
    val avance: Float
)