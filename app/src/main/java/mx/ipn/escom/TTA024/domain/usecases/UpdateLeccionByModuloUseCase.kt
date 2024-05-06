package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.LeccionRepository
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.domain.model.Leccion
import javax.inject.Inject

class UpdateLeccionByModuloUseCase @Inject constructor(private val repository: LeccionRepository) {
    suspend operator fun invoke(leccion: Leccion): String {
        val leccionModel: LeccionModel = LeccionModel(
            leccion.idLeccion,
            leccion.tituloLeccion,
            leccion.descripcionLeccion,
            leccion.nivelLeccion,
            leccion.idModulo,
            leccion.recursoMultimedia
        )
        val response = repository.updateLeccionByModuloFromAPI(
            leccion.idModulo,
            leccion.idLeccion,
            leccionModel
        )

        return response
    }
}