package mx.ipn.escom.TTA024.domain.model

import mx.ipn.escom.TTA024.data.models.UsuarioModel

data class Usuario(
                    var sub: String="",
                    var user_id: String="",
                   var name: String="",
                   var email: String="",
)
fun UsuarioModel.toDomain() = Usuario(sub=sub,name=name, email = email)

