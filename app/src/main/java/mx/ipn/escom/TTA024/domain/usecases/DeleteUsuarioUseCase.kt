package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.data.UsuarioRepository

class DeleteUsuarioUseCase {
    private val repository: UsuarioRepository = UsuarioRepository()
    suspend operator fun invoke(id_usuario: Int): String{
        val response = repository.deleteUsuarioFromApi(id_usuario)

        return response
    }
}