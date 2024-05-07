package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.LeccionRepository
import mx.ipn.escom.TTA024.domain.model.Leccion
class GetLeccionesByModuloUseCase{
    private val repository: LeccionRepository = LeccionRepository()
    suspend operator fun invoke(id_modulo: Int):List<Leccion>{
        val lecciones = repository.getAllLeccionesByModuloFromAPI(id_modulo)

        return lecciones
    }
}