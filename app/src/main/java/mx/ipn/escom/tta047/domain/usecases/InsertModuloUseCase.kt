package mx.ipn.escom.tta047.domain.usecases

import mx.ipn.escom.tta047.data.ModuloRepository
import mx.ipn.escom.tta047.data.models.ModuloModel
import mx.ipn.escom.tta047.domain.model.Modulo

class InsertModuloUseCase{
    private val repository: ModuloRepository = ModuloRepository()
    suspend operator fun invoke(modulo: Modulo):String{
        val moduloModel = ModuloModel(modulo.idModulo, modulo.nombreModulo,modulo.tema)
        val response = repository.insertModuloFromAPI(moduloModel)

        return response
    }
}