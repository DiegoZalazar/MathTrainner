package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.ModuloModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ModuloApiClient {
    @GET("/dev/modulos")
    suspend fun getAllModulos(): Response<List<ModuloModel>>

    @POST("/dev/modulos")
    suspend fun insertModulo(@Body moduloModel: ModuloModel): Response<List<ModuloModel>>

    @PUT("/dev/modulos/{id}")
    suspend fun updateModulo(@Path("id") id_modulo: Int,@Body moduloModel: ModuloModel): Response<List<ModuloModel>>

    @DELETE("/dev/modulos/{id}")
    suspend fun deleteModulo(@Path("id") id_modulo: Int): Response<List<ModuloModel>>
}