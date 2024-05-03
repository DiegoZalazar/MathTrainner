package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EstudianteRepository
import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.domain.model.Estudiante
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class GetEstudiantesUseCase @Inject constructor(private val repository: EstudianteRepository) {
    suspend operator fun invoke():List<Estudiante>{
        val estudiantes = repository.getAllEstudiantesFromAPI()

        return estudiantes
    }
}