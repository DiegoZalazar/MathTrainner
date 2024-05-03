package mx.ipn.escom.TTA024.domain.usecases

import mx.ipn.escom.TTA024.data.ModuloRepository
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.domain.model.Modulo
import javax.inject.Inject

class InsertModuloUseCase @Inject constructor(private val repository: ModuloRepository) {
    suspend operator fun invoke(modulo: Modulo):String{
        val moduloModel = ModuloModel(modulo.idModulo, modulo.nombreModulo)
        val response = repository.insertModuloFromAPI(moduloModel)

        return response
    }
}