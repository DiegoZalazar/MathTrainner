package mx.ipn.escom.TTA024.data

import mx.ipn.escom.TTA024.data.models.Modulo
import mx.ipn.escom.TTA024.data.models.ModuloProvider
import mx.ipn.escom.TTA024.data.network.ModuloService

class ModuloRepository {
    private val api = ModuloService()

    suspend fun getAllModulos(): List<Modulo>{
        val response = api.getModulos()
        ModuloProvider.modulos = response
        return response
    }
}