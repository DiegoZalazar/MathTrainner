package mx.ipn.escom.tta047.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.tta047.data.models.LeccionModel
import mx.ipn.escom.tta047.di.RetrofitHelper

class LeccionService{
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun insertLeccion(id_modulo: Int,leccionModel: LeccionModel): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(LeccionApiClient::class.java).insertLeccionByModulo(id_modulo,leccionModel)
            Log.i("Created Leccion Result",response.toString())
            if(response.code()==200) response.body()?:"Leccion registrado correctamente" else "error"

        }
    }

    suspend fun updateLeccionByModulo(id_modulo: Int,id_leccion: Int,leccionModel: LeccionModel): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(LeccionApiClient::class.java).updateLeccionByModulo(id_modulo,id_leccion,leccionModel)
            Log.i("Updated Leccion Result",response.toString())
            if(response.code()==200) response.body()?:"Leccion actualizado correctamente" else "error"
        }
    }

    suspend fun deleteLeccionByModulo(id_modulo: Int,id_leccion: Int): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(LeccionApiClient::class.java).deleteLeccionByModulo(id_modulo,id_leccion)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Leccion eliminada correctamente" else "error"
        }
    }

    suspend fun getLeccionesByModulo(id_modulo: Int): List<LeccionModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(LeccionApiClient::class.java).getLeccionesByModulo(id_modulo)
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }

}