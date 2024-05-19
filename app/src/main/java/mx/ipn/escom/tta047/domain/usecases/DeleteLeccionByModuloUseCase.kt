package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.LeccionRepository

class DeleteLeccionByModuloUseCase{
    private val repository: LeccionRepository = LeccionRepository()
    suspend operator fun invoke(id_modulo: Int, id_leccion: Int): String{
        val response = repository.deleteLeccionByModuloFromApi(id_modulo,id_leccion)

        return response
    }

}