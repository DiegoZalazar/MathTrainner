package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EstudianteRepository
import mx.ipn.escom.TTA024.domain.model.Estudiante

class GetEstudiantesUseCase{
    private val repository: EstudianteRepository = EstudianteRepository()
    suspend operator fun invoke():List<Estudiante>{
        val estudiantes = repository.getAllEstudiantesFromAPI()

        return estudiantes
    }
}