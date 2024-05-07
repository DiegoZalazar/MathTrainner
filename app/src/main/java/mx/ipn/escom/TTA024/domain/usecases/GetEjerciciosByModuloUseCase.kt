package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EjercicioRepository
import mx.ipn.escom.TTA024.domain.model.Ejercicio

class GetEjerciciosByModuloUseCase{
    private val repository: EjercicioRepository = EjercicioRepository()
    suspend operator fun invoke(id_modulo: Int):List<Ejercicio>{
        val ejercicios = repository.getAllEjerciciosByModuloFromAPI(id_modulo)

        return ejercicios
    }
}