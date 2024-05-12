package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.UsuarioRepository
import mx.ipn.escom.TTA024.domain.model.Usuario

class GetUsuariosUseCase{
    private val repository: UsuarioRepository = UsuarioRepository()
    suspend operator fun invoke():List<Usuario>{
        val estudiantes = repository.getAllEstudiantesFromAPI()

        return estudiantes
    }
}