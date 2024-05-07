package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.network.LeccionService
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.toDomain

class LeccionRepository{
    private val api: LeccionService = LeccionService()
    suspend fun insertLeccionByModuloFromAPI(id_modulo: Int,leccionModel: LeccionModel): String {
        val response: String = api.insertLeccion(id_modulo,leccionModel)
        return response
    }

    suspend fun updateLeccionByModuloFromAPI(id_modulo: Int,id_leccion: Int,leccionModel: LeccionModel): String {
        val response: String = api.updateLeccionByModulo(id_modulo,id_leccion,leccionModel)
        return response
    }

    suspend fun deleteLeccionByModuloFromApi(id_modulo: Int,id_leccion: Int): String {
        val response: String = api.deleteLeccionByModulo(id_modulo,id_leccion)
        return response
    }

    suspend fun getAllLeccionesByModuloFromAPI(id_modulo: Int): List<Leccion> {
        val response: List<LeccionModel> = api.getLeccionesByModulo(id_modulo)
        return response.map { it.toDomain() }
    }
}