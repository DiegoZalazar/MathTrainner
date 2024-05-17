package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.LeccionRepository
import mx.ipn.escom.tta047.domain.model.Leccion
class GetLeccionesByModuloUseCase{
    private val repository: LeccionRepository = LeccionRepository()
    suspend operator fun invoke(id_modulo: Int):List<Leccion>{
        val lecciones = repository.getAllLeccionesByModuloFromAPI(id_modulo)

        return lecciones
    }
}