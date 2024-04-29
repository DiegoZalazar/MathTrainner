package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.ModuloModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LeccionApiClient {
    @GET("/Prod/lecciones")
    suspend fun getAllLecciones(): Response<List<LeccionModel>>
    @POST("/Prod/lecciones")
    suspend fun insertLeccion(@Body leccionModel: LeccionModel): Response<String>

    @PUT("/Prod/lecciones/{id}")
    suspend fun updateLeccion(@Path("id") id_leccion: Int, @Body leccionModel: LeccionModel): Response<List<LeccionModel>>

    @DELETE("/Prod/lecciones/{id}")
    suspend fun deleteModulo(@Path("id") id_leccion: Int): Response<String>
}