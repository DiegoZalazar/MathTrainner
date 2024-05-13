package mx.ipn.escom.TTA024.data.models

import com.google.gson.annotations.SerializedName

data class UsuarioModel(
                        @SerializedName("sub")var sub: String = "",
                        @SerializedName("user_id")var user_id: String = "",
                        @SerializedName("name") var name: String,
                        @SerializedName("email") var email: String,
    ){

}
