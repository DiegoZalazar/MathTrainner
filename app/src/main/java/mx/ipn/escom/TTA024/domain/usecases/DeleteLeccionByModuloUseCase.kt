package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.LeccionRepository
import mx.ipn.escom.TTA024.data.ModuloRepository
import javax.inject.Inject

class DeleteLeccionByModuloUseCase @Inject constructor(private val repository: LeccionRepository) {
    suspend operator fun invoke(id_modulo: Int, id_leccion: Int): String{
        val response = repository.deleteLeccionByModuloFromApi(id_modulo,id_leccion)

        return response
    }

}