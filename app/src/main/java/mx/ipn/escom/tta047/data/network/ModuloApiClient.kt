package mx.ipn.escom.tta047.data.network

import mx.ipn.escom.tta047.data.models.EjercicioModel
import mx.ipn.escom.tta047.data.models.ModuloModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ModuloApiClient {
    @GET("/Prod/modulos")
    suspend fun getAllModulos(): Response<List<ModuloModel>>
    @POST("/Prod/modulos")
    suspend fun insertModulo(@Body moduloModel: ModuloModel): Response<String>

    @PUT("/Prod/modulos/{id}")
    suspend fun updateModulo(@Path("id") id_modulo: Int,@Body moduloModel: ModuloModel): Response<List<ModuloModel>>

    @DELETE("/Prod/modulos/{id}")
    suspend fun deleteModulo(@Path("id") id_modulo: Int): Response<String>



    @GET("/Prod/modulos/{id}/ejercicios") //Modificar endPoint
    suspend fun getEjerciciosByModulo(): Response<List<EjercicioModel>>
}