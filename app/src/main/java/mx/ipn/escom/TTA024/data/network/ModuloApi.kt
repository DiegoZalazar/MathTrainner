package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.Modulo
import retrofit2.Response
import retrofit2.http.GET

interface ModuloApi {
    @GET("/modulos")
    suspend fun getAllModulos(): Response<List<Modulo>>
}