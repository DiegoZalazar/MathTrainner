package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.data.models.EstudianteModel
import mx.ipn.escom.TTA024.data.models.LeccionModel
import javax.inject.Inject

class LeccionService @Inject constructor(private val api: LeccionApiClient) {

    suspend fun insertLeccion(id_modulo: Int,leccionModel: LeccionModel): String{
        return withContext(Dispatchers.IO) {
            var response = api.insertLeccionByModulo(id_modulo,leccionModel)
            Log.i("Created Leccion Result",response.toString())
            if(response.code()==200) response.body()?:"Leccion registrado correctamente" else "error"

        }
    }

    suspend fun updateLeccionByModulo(id_modulo: Int,id_leccion: Int,leccionModel: LeccionModel): String{
        return withContext(Dispatchers.IO) {
            var response = api.updateLeccionByModulo(id_modulo,id_leccion,leccionModel)
            Log.i("Updated Leccion Result",response.toString())
            if(response.code()==200) response.body()?:"Leccion actualizado correctamente" else "error"
        }
    }

    suspend fun deleteLeccionByModulo(id_modulo: Int,id_leccion: Int): String{
        return withContext(Dispatchers.IO) {
            var response = api.deleteLeccionByModulo(id_modulo,id_leccion)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Leccion eliminada correctamente" else "error"
        }
    }

    suspend fun getLeccionesByModulo(id_modulo: Int): List<LeccionModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getLeccionesByModulo(id_modulo)
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }

}