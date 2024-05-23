package mx.ipn.escom.tta047.data.network

import mx.ipn.escom.tta047.data.models.UsuarioModel;
import mx.ipn.escom.tta047.data.models.UsuarioModelDelete
import mx.ipn.escom.tta047.data.models.UsuarioModelUpdate
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.PUT;

interface UsuariosApiClient {
    @GET("/Prod/admins/usuarios")
    suspend fun getAllUsuarios(): Response<List<UsuarioModel>>
    @Headers("Content-Type: text/html")
    @PUT("/Prod/admins/usuarios")
    suspend fun updateEstudiante(
            @Body usuarioModel: UsuarioModelUpdate
    ): Response<String>

    @HTTP(method = "DELETE", path = "/Prod/admins/usuarios", hasBody = true)
    //@DELETE("/Prod/admins/usuarios")
    suspend fun deleteEstudiante(@Body usuarioModel: UsuarioModelDelete): Response<String>

}
