package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.data.models.UsuarioModel
import mx.ipn.escom.TTA024.data.models.UsuarioModelUpdate
import mx.ipn.escom.TTA024.data.network.UsuariosService
import mx.ipn.escom.TTA024.domain.model.Usuario
import mx.ipn.escom.TTA024.domain.model.toDomain

class UsuarioRepository  {
    private val api: UsuariosService = UsuariosService()
    suspend fun getAllEstudiantesFromAPI(): List<Usuario> {
        val response: List<UsuarioModel> = api.getUsuarios()
        return response.map { it.toDomain() }
    }

    suspend fun deleteUsuarioFromApi(usuarioModel: UsuarioModel): String {
        val response: String = api.deleteUsuario(usuarioModel)
        return response
    }

    suspend fun updateUsuario(usuarioModel: UsuarioModelUpdate): String {
        val response: String = api.updateUsuario(usuarioModel)
        return response
    }


}