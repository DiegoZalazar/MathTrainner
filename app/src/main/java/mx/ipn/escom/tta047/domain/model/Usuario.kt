package mx.ipn.escom.tta047.domain.model

import mx.ipn.escom.tta047.data.models.UsuarioModel

data class Usuario(
                    var sub: String="",
                    var user_id: String="",
                   var name: String="",
                   var email: String="",
)
fun UsuarioModel.toDomain() = Usuario(sub=sub,name=name, email = email)

