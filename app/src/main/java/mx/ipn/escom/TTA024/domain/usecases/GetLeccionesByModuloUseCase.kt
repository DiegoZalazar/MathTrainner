package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.LeccionRepository
import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class GetLeccionesByModuloUseCase @Inject constructor(private val repository: LeccionRepository) {
    suspend operator fun invoke(id_modulo: Int):List<Leccion>{
        val lecciones = repository.getAllLeccionesByModuloFromAPI(id_modulo)

        return lecciones
    }
}