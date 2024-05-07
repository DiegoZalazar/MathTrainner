package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.data.network.EjercicioService
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.toDomain

class EjercicioRepository {
    private val api: EjercicioService = EjercicioService()
    suspend fun insertEjercicioByModuloFromAPI(id_modulo: Int,ejercicioModel: EjercicioModel): String {
        val response: String = api.insertEjercicio(id_modulo,ejercicioModel)
        return response
    }

    suspend fun updateEjercicioByModuloFromAPI(id_modulo: Int,id_ejercicio: Int,ejercicioModel: EjercicioModel): String {
        val response: String = api.updateEjercicioByModulo(id_modulo,id_ejercicio,ejercicioModel)
        return response
    }

    suspend fun deleteEjercicioByModuloFromApi(id_modulo: Int,id_ejercicio: Int): String {
        val response: String = api.deleteEjercicioByModulo(id_modulo,id_ejercicio)
        return response
    }

    suspend fun getAllEjerciciosByModuloFromAPI(id_modulo: Int): List<Ejercicio> {
        val response: List<EjercicioModel> = api.getEjercicioByModulo(id_modulo)
        return response.map { it.toDomain() }
    }
}