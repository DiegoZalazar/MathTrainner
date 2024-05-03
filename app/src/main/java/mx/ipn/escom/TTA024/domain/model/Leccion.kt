package mx.ipn.escom.TTA024.domain.model

import com.google.gson.annotations.SerializedName
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.ModuloModel

data class Leccion(val idLeccion: Int,
                   var tituloLeccion: String,
                   var descripcionLeccion: String,
                   var nivelLeccion: Int,
                   var idModulo: Int,
                   var recursoMultimedia: String)

fun LeccionModel.toDomain() = Leccion(idLeccion, tituloLeccion,descripcionLeccion,nivelLeccion,idModulo,recursoMultimedia)

