package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.UsuarioRepository
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.UsuarioModel
import mx.ipn.escom.TTA024.data.models.UsuarioModelUpdate
import mx.ipn.escom.TTA024.domain.model.Usuario

class UpdateUsuarioUseCase {

    private val repository: UsuarioRepository = UsuarioRepository()
    suspend operator fun invoke(usuario: Usuario): String {
        val usuarioModel: UsuarioModelUpdate = UsuarioModelUpdate(
            usuario.user_id,
            usuario.name,
            usuario.email
        )
        val response = repository.updateUsuario(
            usuarioModel
        )

        return response
    }
}