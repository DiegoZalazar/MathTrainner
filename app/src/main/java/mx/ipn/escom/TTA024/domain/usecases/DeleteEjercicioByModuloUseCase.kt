package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EjercicioRepository
import javax.inject.Inject

class DeleteEjercicioByModuloUseCase @Inject constructor(private val repository: EjercicioRepository) {
    suspend operator fun invoke(id_modulo: Int, id_ejercicio: Int): String{
        val response = repository.deleteEjercicioByModuloFromApi(id_modulo,id_ejercicio)

        return response
    }
}