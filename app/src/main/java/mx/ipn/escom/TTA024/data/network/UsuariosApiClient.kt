package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.UsuarioModel;
import mx.ipn.escom.TTA024.data.models.UsuarioModelUpdate
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path

interface UsuariosApiClient {
    @GET("/Prod/admins/usuarios")
    suspend fun getAllUsuarios(): Response<List<UsuarioModel>>
    @Headers("Content-Type: text/html")
    @PUT("/Prod/admins/usuarios")
    suspend fun updateEstudiante(
            @Body usuarioModel: UsuarioModelUpdate
    ): Response<String>

    @DELETE("/Prod/admins/usuarios")
    suspend fun deleteEstudiante(@Body usuarioModel: UsuarioModel): Response<String>

}
