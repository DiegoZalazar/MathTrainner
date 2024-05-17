package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.ModuloRepository
import mx.ipn.escom.tta047.domain.model.Modulo

class GetModulosUseCase{
    private val repository: ModuloRepository = ModuloRepository()
    suspend operator fun invoke():List<Modulo>{
        val modulos = repository.getAllModulosFromAPI()

        return modulos
    }
}