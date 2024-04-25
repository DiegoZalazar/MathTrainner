package mx.ipn.escom.TTA024.domain

import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class GetModulosUseCase @Inject constructor(private val repository: ModuloRepository) {
    suspend operator fun invoke():List<Modulo>{
        val quotes = repository.getAllModulosFromAPI()

        return quotes
    }
}