package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.EjercicioRepository

class DeleteEjercicioByModuloUseCase{
    private val repository: EjercicioRepository = EjercicioRepository()
    suspend operator fun invoke(id_modulo: Int, id_ejercicio: Int): String{
        val response = repository.deleteEjercicioByModuloFromApi(id_modulo,id_ejercicio)

        return response
    }
}