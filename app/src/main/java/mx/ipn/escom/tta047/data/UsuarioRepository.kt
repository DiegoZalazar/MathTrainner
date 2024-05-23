package mx.ipn.escom.tta047.data

import mx.ipn.escom.tta047.data.models.UsuarioModel
import mx.ipn.escom.tta047.data.models.UsuarioModelDelete
import mx.ipn.escom.tta047.data.models.UsuarioModelUpdate
import mx.ipn.escom.tta047.data.network.UsuariosService
import mx.ipn.escom.tta047.domain.model.Usuario
import mx.ipn.escom.tta047.domain.model.toDomain

class UsuarioRepository  {
    private val api: UsuariosService = UsuariosService()
    suspend fun getAllEstudiantesFromAPI(): List<Usuario> {
        val response: List<UsuarioModel> = api.getUsuarios()
        return response.map { it.toDomain() }
    }

    suspend fun deleteUsuarioFromApi(usuarioModel: UsuarioModelDelete): String {
        val response: String = api.deleteUsuario(usuarioModel)
        return response
    }

    suspend fun updateUsuario(usuarioModel: UsuarioModelUpdate): String {
        val response: String = api.updateUsuario(usuarioModel)
        return response
    }


}