package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EjercicioRepository
import mx.ipn.escom.TTA024.data.LeccionRepository
import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Leccion
import javax.inject.Inject

class UpdateEjercicioByModuloUseCase @Inject constructor(private val repository: EjercicioRepository) {
    suspend operator fun invoke(ejercicio: Ejercicio): String {
        val ejercicioModel: EjercicioModel = EjercicioModel(ejercicio.idEjercicio, ejercicio.tiempoEjercicio, ejercicio.nivelEjercicio, ejercicio.planteamientoEjercicio, ejercicio.respCorrectaEjercicio, ejercicio.respIncorrectasEjercicio, ejercicio.paresCorrectosEjercicio, ejercicio.idExamen, ejercicio.idModulo)
        val response = repository.updateEjercicioByModuloFromAPI(
            ejercicioModel.idModulo,
            ejercicioModel.idEjercicio,
            ejercicioModel
        )

        return response
    }
}