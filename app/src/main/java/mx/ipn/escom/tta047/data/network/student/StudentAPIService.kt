package mx.ipn.escom.tta047.data.network.student

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL = "https://w8b6as9g2h.execute-api.us-east-1.amazonaws.com/Prod/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface StudentAPIService {
    @GET("estudiante/modulos")
    suspend fun getModulos() : List<Modulo>

    @GET("modulos/{idMod}/lecciones")
    suspend fun getLeccion(@Path("idMod") idModulo: Int) : List<Leccion>

    @GET("estudiante/sesion/ejercicios/{idMod}")
    suspend fun getEjerciciosModulo(@Path("idMod") idModulo: Int) : List<EjercicioGeneral>

    @POST("estudiante/sesion/ejercicios/{idMod}")
    suspend fun postEjerciciosResultados(@Path("idMod") idModulo: Int,@Body respuestas: Respuestas) : AvanceModulo

    @PUT("estudiante/modulos")
    suspend fun borrarAvance()

    @GET("estudiante/examen/info")
    suspend fun getExamenDone() : Boolean
}

object  StudentAPI {
    val retrofitService : StudentAPIService by lazy {
        retrofit.create(StudentAPIService::class.java)
    }
}