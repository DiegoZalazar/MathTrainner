package mx.ipn.escom.TTA024.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.core.RetrofitHelper
import mx.ipn.escom.TTA024.data.models.ModuloModel
import retrofit2.Response
import javax.inject.Inject

class ModuloService @Inject constructor(private val api:ModuloApiClient) {

    suspend fun getModulos(): List<ModuloModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllModulos()
            response.body() ?: emptyList()
        }
    }

}