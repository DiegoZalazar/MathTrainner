package mx.ipn.escom.tta047.data.network

import mx.ipn.escom.tta047.data.models.EjercicioModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EjercicioApiClient {
    @PUT("/Prod/modulos/{idm}/ejercicios/{idl}")
    suspend fun updateEjercicioByModulo(@Path("idm") id_modulo: Int,
                                      @Path("idl") id_ejercicio: Int,
                                      @Body leccionModel: EjercicioModel
    ): Response<String>


    @POST("/Prod/modulos/{idm}/ejercicios")
    suspend fun insertEjercicioByModulo(@Path("idm") id_modulo: Int,
                                      @Body leccionModel: EjercicioModel
    ): Response<String>
    @DELETE("/Prod/modulos/{idm}/ejercicios/{idl}")
    suspend fun deleteEjercicioByModulo(
        @Path("idm") id_modulo: Int,
        @Path("idl") id_ejercicio: Int,): Response<String>

    @GET("/Prod/modulos/{id}/ejercicios") //Modificar endPoint
    suspend fun getEjercicioByModulo(@Path("id") id_modulo: Int): Response<List<EjercicioModel>>
}