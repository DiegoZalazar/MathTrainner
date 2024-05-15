package mx.ipn.escom.TTA024.data.network.student

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("estudiante/sesion/ejercicios/{idMod}")
    suspend fun postEjerciciosResultados(@Path("idMod") idModulo: Int, respuestas: Respuestas)
}

object  StudentAPI {
    val retrofitService : StudentAPIService by lazy {
        retrofit.create(StudentAPIService::class.java)
    }
}