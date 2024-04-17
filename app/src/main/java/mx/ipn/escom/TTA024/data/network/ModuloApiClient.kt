package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.ModuloModel
import retrofit2.Response
import retrofit2.http.GET

interface ModuloApiClient {
    @GET("/modulos")
    suspend fun getAllModulos(): Response<List<ModuloModel>>
}