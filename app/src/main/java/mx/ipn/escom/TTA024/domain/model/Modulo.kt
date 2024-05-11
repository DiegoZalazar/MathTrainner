package mx.ipn.escom.TTA024.domain.model

import mx.ipn.escom.TTA024.data.models.ModuloModel

data class Modulo (val idModulo: Int,
                  val nombreModulo: String,
                    val idTema: Int)

fun ModuloModel.toDomain() = Modulo(idModulo, nombreModulo,idTema)
//fun ModuloModel.toDomain() = Modulo(idModulo, nombreModulo)