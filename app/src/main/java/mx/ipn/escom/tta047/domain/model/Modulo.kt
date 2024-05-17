package mx.ipn.escom.tta047.domain.model

import mx.ipn.escom.tta047.data.models.ModuloModel

data class Modulo (val idModulo: Int,
                  val nombreModulo: String,
                   val tema: String)

fun ModuloModel.toDomain() = Modulo(idModulo, nombreModulo,tema)
//fun ModuloModel.toDomain() = Modulo(idModulo, nombreModulo)