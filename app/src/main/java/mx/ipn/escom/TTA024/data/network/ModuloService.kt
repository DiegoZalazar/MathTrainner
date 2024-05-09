package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.di.RetrofitHelper

class ModuloService{
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getModulos(): List<ModuloModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(ModuloApiClient::class.java).getAllModulos()
            Log.i("ResultAPI Get",response.toString())
            response.body() ?: emptyList()
        }
    }

    suspend fun insertModulo(moduloModel: ModuloModel): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(ModuloApiClient::class.java).insertModulo(moduloModel)
            Log.i("ResultAPI Insert",response.toString())
            if(response.code()==200) response.body()?:"Módulo registrado correctamente" else "error"
        }
    }

    suspend fun deleteModulo(id_modulo: Int): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(ModuloApiClient::class.java).deleteModulo(id_modulo)
            Log.i("ResultAPI Delete",response.toString())
            if(response.code()==200) response.body()?:"Módulo registrado correctamente" else "error"
        }
    }

}