package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class GetModulosUseCase @Inject constructor(private val repository: ModuloRepository) {
    suspend operator fun invoke():List<Modulo>{
        val modulos = repository.getAllModulosFromAPI()

        return modulos
    }
}