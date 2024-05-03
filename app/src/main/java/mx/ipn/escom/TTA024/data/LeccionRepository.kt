package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.data.network.LeccionService
import mx.ipn.escom.TTA024.data.network.ModuloService
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.model.toDomain
import javax.inject.Inject

class LeccionRepository @Inject constructor(
    private val api: LeccionService, //private val quoteDao: QuoteDao
) {
    suspend fun getAllLeccionesFromAPI(): List<Leccion> {
        val response: List<LeccionModel> = api.getLecciones()
        return response.map { it.toDomain() }
    }

    suspend fun insertLeccionFromAPI(leccionModel: LeccionModel): String {
        val response: String = api.insertLeccion(leccionModel)
        return response
    }

    suspend fun deleteLeccionFromApi(id_leccion: Int): String {
        val response: String = api.deleteLeccion(id_leccion)
        return response
    }

    suspend fun getAllLeccionesByModuloFromAPI(id_modulo: Int): List<Leccion> {
        val response: List<LeccionModel> = api.getLeccionesByModulo(id_modulo)
        return response.map { it.toDomain() }
    }
}