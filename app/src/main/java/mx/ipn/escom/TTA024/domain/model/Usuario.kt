package mx.ipn.escom.TTA024.domain.model

import mx.ipn.escom.TTA024.data.models.UsuarioModel

data class Usuario(val user_id: Int,
                   var name: String,
                   var email: String,
)
fun UsuarioModel.toDomain() = Usuario(user_id, name,email)

