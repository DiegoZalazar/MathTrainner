package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.EjercicioRepository
import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import javax.inject.Inject

class InsertEjercicioByModuloUseCase @Inject constructor(private val repository: EjercicioRepository) {
    suspend operator fun invoke(ejercicio: Ejercicio):String{
        val ejercicioModel: EjercicioModel =EjercicioModel(ejercicio.idEjercicio, ejercicio.tiempoEjercicio, ejercicio.nivelEjercicio, ejercicio.planteamientoEjercicio, ejercicio.respCorrectaEjercicio, ejercicio.respIncorrectasEjercicio, ejercicio.paresCorrectosEjercicio, ejercicio.idExamen, ejercicio.idModulo)
        val response = repository.insertEjercicioByModuloFromAPI(ejercicio.idModulo,ejercicioModel)

        return response
    }
}