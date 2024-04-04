package mx.ipn.escom.TTA024.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.core.RetrofitHelper
import mx.ipn.escom.TTA024.data.models.Modulo
import retrofit2.Response

class ModuloService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getModulos(): List<Modulo>{
        return withContext(Dispatchers.IO){
            val response: Response<List<Modulo>> = retrofit.create(ModuloApi::class.java).getAllModulos()
            response.body()?: emptyList()
        }

    }
}