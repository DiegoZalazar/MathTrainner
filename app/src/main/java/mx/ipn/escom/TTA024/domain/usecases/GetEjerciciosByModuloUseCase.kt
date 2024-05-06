package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EjercicioRepository
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import javax.inject.Inject

class GetEjerciciosByModuloUseCase @Inject constructor(private val repository: EjercicioRepository) {
    suspend operator fun invoke(id_modulo: Int):List<Ejercicio>{
        val ejercicios = repository.getAllEjerciciosByModuloFromAPI(id_modulo)

        return ejercicios
    }
}