package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
//import mx.ipn.escom.TTA024.core.RetrofitHelper
import mx.ipn.escom.TTA024.data.models.ModuloModel
import retrofit2.Response
import javax.inject.Inject

class ModuloService @Inject constructor(private val api:ModuloApiClient) {

    suspend fun getModulos(): List<ModuloModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllModulos()
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }

    suspend fun insertModulo(moduloModel: ModuloModel): List<ModuloModel>{
        return withContext(Dispatchers.IO) {
            val response = api.insertModulo(moduloModel)
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }

}