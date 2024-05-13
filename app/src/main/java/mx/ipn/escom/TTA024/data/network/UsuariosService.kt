package mx.ipn.escom.TTA024.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.UsuarioModel
import mx.ipn.escom.TTA024.data.models.UsuarioModelUpdate
import mx.ipn.escom.TTA024.di.RetrofitHelper

class UsuariosService{

    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getUsuarios(): List<UsuarioModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UsuariosApiClient::class.java).getAllUsuarios()
            Log.i("ResultAPI",response.toString())
            response.body() ?: emptyList()
        }
    }

    suspend fun deleteUsuario(usuarioModel: UsuarioModel): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(UsuariosApiClient::class.java).deleteEstudiante(usuarioModel)
            Log.i("ResultAPI",response.toString())
            if(response.code()==200) response.body()?:"Estudiante eliminado correctamente" else "error"
        }
    }

    suspend fun updateUsuario(usuarioModel: UsuarioModelUpdate): String{
        return withContext(Dispatchers.IO) {
            var response = retrofit.create(UsuariosApiClient::class.java).updateEstudiante(usuarioModel)
            Log.i("Updated Leccion Result",response.toString())
            if(response.code()==200) response.body()?:"Leccion actualizado correctamente" else "error"
        }
    }


}