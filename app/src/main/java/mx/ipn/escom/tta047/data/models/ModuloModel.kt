package mx.ipn.escom.tta047.data.models

import com.google.gson.annotations.SerializedName

data class ModuloModel(@SerializedName("idModulo") val idModulo: Int,
                       @SerializedName("nombreModulo")  val nombreModulo: String,
                       @SerializedName("tema") val tema: String){

}
