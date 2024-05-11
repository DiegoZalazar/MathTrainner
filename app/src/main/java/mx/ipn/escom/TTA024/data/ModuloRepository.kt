package mx.ipn.escom.TTA024.data

import android.util.Log
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.data.network.ModuloService
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.model.toDomain


class ModuloRepository {
    private val api: ModuloService = ModuloService()
    suspend fun getAllModulosFromAPI(): List<Modulo> {
        Log.i("ModuloRepository", "Getting modulos")
        val response: List<ModuloModel> = api.getModulos()
        return response.map { it.toDomain() }
    }

    suspend fun insertModuloFromAPI(moduloModel: ModuloModel): String {
        val response: String = api.insertModulo(moduloModel)
        return response
    }

    suspend fun deleteModuloFromApi(id_modulo: Int): String {
        val response: String = api.deleteModulo(id_modulo)
        return response
    }

    /*suspend fun getAllQuotesFromDatabase():List<Quote>{
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }*/

    /*suspend fun insertQuotes(quotes:List<QuoteEntity>){
        quoteDao.insertAll(quotes)
    }*/

    /*suspend fun clearQuotes(){
        quoteDao.deleteAllQuotes()
    }*/
}