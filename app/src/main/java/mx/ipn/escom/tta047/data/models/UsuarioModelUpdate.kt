package mx.ipn.escom.tta047.data.models

import com.google.gson.annotations.SerializedName

data class UsuarioModelUpdate (
    @SerializedName("user_id")var user_id: String,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
){

}