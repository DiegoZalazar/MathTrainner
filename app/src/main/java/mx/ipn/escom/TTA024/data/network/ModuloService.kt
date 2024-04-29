package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun insertModulo(moduloModel: ModuloModel): String{
        return withContext(Dispatchers.IO) {
            var response = api.insertModulo(moduloModel)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Módulo registrado correctamente" else "error"
        }
    }

    suspend fun deleteModulo(id_modulo: Int): String{
        return withContext(Dispatchers.IO) {
            var response = api.deleteModulo(id_modulo)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Módulo registrado correctamente" else "error"
        }
    }

}