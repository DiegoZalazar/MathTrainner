package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.ModuloRepository
class DeleteModuloUseCase{
    private val repository: ModuloRepository = ModuloRepository()
    suspend operator fun invoke(id_modulo: Int): String{
        val response = repository.deleteModuloFromApi(id_modulo)

        return response
    }
}