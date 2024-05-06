package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.data.models.LeccionModel
import javax.inject.Inject

class EjercicioService @Inject constructor(private val api: EjercicioApiClient)  {
    suspend fun insertEjercicio(id_modulo: Int,ejercicioModel: EjercicioModel): String{
        return withContext(Dispatchers.IO) {
            var response = api.insertEjercicioByModulo(id_modulo,ejercicioModel)
            Log.i("Created Ejercicio Result",response.toString())
            if(response.code()==200) response.body()?:"Ejercicio registrado correctamente" else "error"

        }
    }

    suspend fun updateEjercicioByModulo(id_modulo: Int,id_ejercicio: Int,ejercicioModel: EjercicioModel): String{
        return withContext(Dispatchers.IO) {
            var response = api.updateEjercicioByModulo(id_modulo,id_ejercicio,ejercicioModel)
            Log.i("Updated Ejercicio Result",response.toString())
            if(response.code()==200) response.body()?:"Ejercicio actualizado correctamente" else "error"
        }
    }

    suspend fun deleteEjercicioByModulo(id_modulo: Int,id_ejercicio: Int): String{
        return withContext(Dispatchers.IO) {
            var response = api.deleteEjercicioByModulo(id_modulo,id_ejercicio)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Ejercicio eliminada correctamente" else "error"
        }
    }

    suspend fun getEjercicioByModulo(id_modulo: Int): List<EjercicioModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getEjercicioByModulo(id_modulo)
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }
}