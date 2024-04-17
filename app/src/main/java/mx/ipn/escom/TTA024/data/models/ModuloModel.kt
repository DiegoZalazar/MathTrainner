package mx.ipn.escom.TTA024.data.models

import com.google.gson.annotations.SerializedName

data class ModuloModel(@SerializedName("idModulo") val idModulo: Int,
                       @SerializedName("nombreModulo")  val nombreModulo: String){

}
