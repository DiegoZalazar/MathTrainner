package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EjercicioRepository
import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.domain.model.Ejercicio

class UpdateEjercicioByModuloUseCase{
    private val repository: EjercicioRepository = EjercicioRepository()
    suspend operator fun invoke(ejercicio: Ejercicio): String {
        val ejercicioModel: EjercicioModel = EjercicioModel(ejercicio.idEjercicio,ejercicio.cuerpo,ejercicio.tipo, ejercicio.tiempoEjercicio, ejercicio.nivelEjercicio, ejercicio.planteamientoEjercicio, ejercicio.respCorrectaEjercicio, ejercicio.respIncorrectasEjercicio, ejercicio.paresCorrectosEjercicio, ejercicio.idExamen, ejercicio.idModulo)
        val response = repository.updateEjercicioByModuloFromAPI(
            ejercicioModel.idModulo,
            ejercicioModel.idEjercicio,
            ejercicioModel
        )

        return response
    }
}