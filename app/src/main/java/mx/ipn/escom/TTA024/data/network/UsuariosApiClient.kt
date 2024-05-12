package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.UsuarioModel;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path

interface UsuariosApiClient {
    @GET("/admins/usuarios")
    suspend fun getAllUsuarios(): Response<List<UsuarioModel>>
    @PUT("/admins/usuarios/{id}")
    suspend fun updateEstudiante(@Path("id") id_estudiante: Int,
                                 @Body usuarioModel: UsuarioModel): Response<String>

    @DELETE("/admins/usuarios/{id}")
    suspend fun deleteEstudiante(@Path("id") id_estudiante: Int): Response<String>

}
