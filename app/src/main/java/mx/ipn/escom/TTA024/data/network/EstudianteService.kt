package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.data.models.EstudianteModel
import mx.ipn.escom.TTA024.data.models.ModuloModel
import javax.inject.Inject

class EstudianteService @Inject constructor(private val api: EstudianteApiClient) {
    suspend fun getEstudiantes(): List<EstudianteModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllEstudiantes()
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }

    suspend fun insertEstudiante(estudianteModel: EstudianteModel): String{
        return withContext(Dispatchers.IO) {
            var response = api.insertEstudiante(estudianteModel)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Estudiante registrado correctamente" else "error"
        }
    }

    suspend fun deleteEstudiante(id_estudiante: Int): String{
        return withContext(Dispatchers.IO) {
            var response = api.deleteEstudiante(id_estudiante)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Estudiante registrado correctamente" else "error"
        }
    }


}