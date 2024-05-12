package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.data.models.UsuarioModel
import mx.ipn.escom.TTA024.data.network.UsuariosService
import mx.ipn.escom.TTA024.domain.model.Usuario
import mx.ipn.escom.TTA024.domain.model.toDomain

class UsuarioRepository  {
    private val api: UsuariosService = UsuariosService()
    suspend fun getAllEstudiantesFromAPI(): List<Usuario> {
        val response: List<UsuarioModel> = api.getUsuarios()
        return response.map { it.toDomain() }
    }

    suspend fun deleteUsuarioFromApi(id_usuario: Int): String {
        val response: String = api.deleteUsuario(id_usuario)
        return response
    }

    suspend fun updateUsuario(id_usuario: Int ,usuarioModel: UsuarioModel): String {
        val response: String = api.updateUsuario(id_usuario,usuarioModel)
        return response
    }


}