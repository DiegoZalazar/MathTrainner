package mx.ipn.escom.TTA024.data

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.data.network.ModuloService
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.model.toDomain
import mx.ipn.escom.TTA024.ui.viewmodels.ModuloViewModel
import javax.inject.Inject


class ModuloRepository @Inject constructor(
    private val api: ModuloService,
    //private val quoteDao: QuoteDao
) {

    suspend fun getAllModulosFromAPI(): List<Modulo> {
        val response: List<ModuloModel> = api.getModulos()
        return response.map { it.toDomain() }
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