package mx.ipn.escom.tta047.data.models

import com.google.gson.annotations.SerializedName

data class LeccionModel(@SerializedName("idLeccion")  val idLeccion: Int,
                        @SerializedName("tituloLeccion")  var tituloLeccion: String,
                        @SerializedName("descripcionLeccion")  var descripcionLeccion: String,
                        @SerializedName("nivelLeccion")  var nivelLeccion: Int,
                        @SerializedName("idModulo") var idModulo: Int,
                        @SerializedName("recursoMultimedia") var recursoMultimedia: String){

}
