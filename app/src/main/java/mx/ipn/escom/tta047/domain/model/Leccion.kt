package mx.ipn.escom.tta047.domain.model

import mx.ipn.escom.tta047.data.models.LeccionModel

data class Leccion(val idLeccion: Int,
                   var tituloLeccion: String,
                   var descripcionLeccion: String,
                   var nivelLeccion: Int,
                   var idModulo: Int,
                   var recursoMultimedia: String)

fun LeccionModel.toDomain() = Leccion(idLeccion, tituloLeccion,descripcionLeccion,nivelLeccion,idModulo,recursoMultimedia)

