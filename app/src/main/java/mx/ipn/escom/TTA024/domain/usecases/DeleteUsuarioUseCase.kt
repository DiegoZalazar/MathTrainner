package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.data.UsuarioRepository
import mx.ipn.escom.TTA024.data.models.UsuarioModel
import mx.ipn.escom.TTA024.domain.model.Usuario

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