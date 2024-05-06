package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.LeccionRepository
import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class InsertLeccionByModuloUseCase @Inject constructor(private val repository: LeccionRepository) {
    suspend operator fun invoke(leccion: Leccion):String{
        val leccionModel:LeccionModel = LeccionModel(leccion.idLeccion,leccion.tituloLeccion,leccion.descripcionLeccion
        ,leccion.nivelLeccion,leccion.idModulo,leccion.recursoMultimedia)
        val response = repository.insertLeccionByModuloFromAPI(leccion.idModulo,leccionModel)

        return response
    }
}