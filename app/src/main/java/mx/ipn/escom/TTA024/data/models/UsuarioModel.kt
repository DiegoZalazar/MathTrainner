package mx.ipn.escom.TTA024.data.models

import com.google.gson.annotations.SerializedName

data class UsuarioModel(
                        @SerializedName("user_id")val user_id: Int,
                        @SerializedName("name") var name: String,
                        @SerializedName("email") var email: String,
    ){

}
