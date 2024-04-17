package mx.ipn.escom.TTA024.domain.model

import mx.ipn.escom.TTA024.data.models.ModuloModel

data class Modulo (val idModulo: Int,
                  val nombreModulo: String)

fun ModuloModel.toDomain() = Modulo(idModulo, nombreModulo)
//fun ModuloModel.toDomain() = Modulo(idModulo, nombreModulo)