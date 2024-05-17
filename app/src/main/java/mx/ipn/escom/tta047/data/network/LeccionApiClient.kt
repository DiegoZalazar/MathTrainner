package mx.ipn.escom.tta047.data.network

import mx.ipn.escom.tta047.data.models.LeccionModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LeccionApiClient {
    @PUT("/Prod/modulos/{idm}/lecciones")
    suspend fun updateLeccionByModulo(@Path("idm") id_modulo: Int,
                                      @Body leccionModel: LeccionModel): Response<String>
    @PUT("/Prod/modulos/{idm}/lecciones/{idl}")
    suspend fun updateLeccionByModulo(@Path("idm") id_modulo: Int,
                                      @Path("idl") id_leccion: Int,
                                      @Body leccionModel: LeccionModel): Response<String>


    @POST("/Prod/modulos/{idm}/lecciones")
    suspend fun insertLeccionByModulo(@Path("idm") id_modulo: Int,
                                      @Body leccionModel: LeccionModel): Response<String>
    @DELETE("/Prod/modulos/{idm}/lecciones/{idl}")
    suspend fun deleteLeccionByModulo(
        @Path("idm") id_modulo: Int,
        @Path("idl") id_leccion: Int,): Response<String>

    @GET("/Prod/modulos/{id}/lecciones") //Modificar endPoint
    suspend fun getLeccionesByModulo(@Path("id") id_modulo: Int): Response<List<LeccionModel>>
}