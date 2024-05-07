package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.ModuloRepository
class DeleteModuloUseCase{
    private val repository: ModuloRepository = ModuloRepository()
    suspend operator fun invoke(id_modulo: Int): String{
        val response = repository.deleteModuloFromApi(id_modulo)

        return response
    }
}