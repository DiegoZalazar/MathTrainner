package mx.ipn.escom.TTA024.data.network

import mx.ipn.escom.TTA024.data.models.EstudianteModel;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path

interface EstudianteApiClient {
    @GET("/Prod/estudiantes")
    suspend fun getAllEstudiantes(): Response<List<EstudianteModel>>

    @POST("/Prod/estudiantes")
    suspend fun insertEstudiante(@Body estudianteModel: EstudianteModel): Response<String>

    @PUT("/Prod/estudiantes/{id}")
    suspend fun updateEstudiante(@Path("id") id_estudiante: Int, @Body estudianteModel: EstudianteModel): Response<List<EstudianteModel>>

    @DELETE("/Prod/estudiantes/{id}")
    suspend fun deleteEstudiante(@Path("id") id_estudiante: Int): Response<String>

}
