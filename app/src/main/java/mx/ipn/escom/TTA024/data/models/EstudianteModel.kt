package mx.ipn.escom.TTA024.data.models

import com.google.gson.annotations.SerializedName

data class EstudianteModel(@SerializedName("idEstudiante") val idEstudiante: Int,
                           @SerializedName("nombreEstudiante") var nombreEstudiante: String,
                           @SerializedName("nombreUsuario") var nombreUsuario: String,
                           @SerializedName("correoEstudiante") var correoEstudiante: String,
                           @SerializedName("estatusEstudiante") var estatusEstudiante: String,
                           @SerializedName("contrasenaEstudiante") var contrasenaEstudiante: String
    ){

}
