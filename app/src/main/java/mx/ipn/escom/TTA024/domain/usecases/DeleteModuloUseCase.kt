package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class DeleteModuloUseCase @Inject constructor(private val repository: ModuloRepository) {
    suspend operator fun invoke(id_modulo: Int): String{
        val response = repository.deleteModuloFromApi(id_modulo)

        return response
    }
}