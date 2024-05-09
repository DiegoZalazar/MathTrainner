package mx.ipn.escom.TTA024.domain.model

import com.google.gson.annotations.SerializedName
import mx.ipn.escom.TTA024.data.models.EjercicioModel


data class Ejercicio(val idEjercicio: Int,
                     var cuerpo: String,
                     var tipo: String,
                     var tiempoEjercicio: Int,
                     var nivelEjercicio: Int,
                     var planteamientoEjercicio: String,
                     var respCorrectaEjercicio: String,
                     var respIncorrectasEjercicio: String,
                     var paresCorrectosEjercicio: String,
                     var idExamen: Int,
                     var idModulo: Int
                          ){

}

fun EjercicioModel.toDomain() = Ejercicio(idEjercicio,cuerpo,tipoEjercicio, tiempoEjercicio, nivelEjercicio, planteamientoEjercicio, respCorrectaEjercicio, respIncorrectasEjercicio, paresCorrectosEjercicio, idExamen, idModulo)