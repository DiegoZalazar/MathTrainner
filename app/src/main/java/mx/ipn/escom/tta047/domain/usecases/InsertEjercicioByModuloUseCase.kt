package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.EjercicioRepository
import mx.ipn.escom.tta047.data.models.EjercicioModel
import mx.ipn.escom.tta047.domain.model.Ejercicio

class InsertEjercicioByModuloUseCase{
    private val repository: EjercicioRepository = EjercicioRepository()
    suspend operator fun invoke(ejercicio: Ejercicio):String{
        val ejercicioModel: EjercicioModel =EjercicioModel(ejercicio.idEjercicio,ejercicio.cuerpo,ejercicio.tipo,ejercicio.tiempoEjercicio, ejercicio.nivelEjercicio, ejercicio.planteamientoEjercicio, ejercicio.respCorrectaEjercicio, ejercicio.respIncorrectasEjercicio, ejercicio.paresCorrectosEjercicio, ejercicio.idExamen, ejercicio.idModulo)
        val response = repository.insertEjercicioByModuloFromAPI(ejercicio.idModulo,ejercicioModel)

        return response
    }
}