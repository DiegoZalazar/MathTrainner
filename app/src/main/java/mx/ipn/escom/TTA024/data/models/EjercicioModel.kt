package mx.ipn.escom.TTA024.data.models

import com.google.gson.annotations.SerializedName

data class EjercicioModel(@SerializedName("idEjercicio") val idEjercicio: Int,
                          @SerializedName("tiempoEjercicio") var tiempoEjercicio: Int,
                          @SerializedName("nivelEjercicio") var nivelEjercicio: Int,
                          @SerializedName("planteamientoEjercicio") var planteamientoEjercicio: String,
                          @SerializedName("respCorrectaEjercicio") var respCorrectaEjercicio: String,
                          @SerializedName("respIncorrectasEjercicio") var respIncorrectasEjercicio: String,
                          @SerializedName("paresCorrectosEjercicio") var paresCorrectosEjercicio: String,
                          @SerializedName("idExamen") var idExamen: Int,
                          @SerializedName("idModulo") var idModulo: Int){

}
