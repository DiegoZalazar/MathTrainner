package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.UsuarioRepository
import mx.ipn.escom.tta047.data.models.UsuarioModel
import mx.ipn.escom.tta047.domain.model.Usuario

class DeleteUsuarioUseCase {
    private val repository: UsuarioRepository = UsuarioRepository()
    suspend operator fun invoke(usuario: Usuario): String{
        val usuarioModel: UsuarioModel = UsuarioModel(
            usuario.sub,
            usuario.user_id,
            usuario.name,
            usuario.email
        )
        val response = repository.deleteUsuarioFromApi(usuarioModel)

        return response
    }
}