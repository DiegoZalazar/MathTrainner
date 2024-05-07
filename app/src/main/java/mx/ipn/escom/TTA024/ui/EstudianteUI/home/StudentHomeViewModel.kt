package mx.ipn.escom.TTA024.ui.EstudianteUI.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.data.network.student.Modulo
import mx.ipn.escom.TTA024.data.network.student.StudentAPIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

private const val BASE_URL = "https://w8b6as9g2h.execute-api.us-east-1.amazonaws.com/Prod/"

data class ModuloUI(
    val pos: Int = 0,
    val modulo: Modulo
)

sealed interface StudentHomeUIState {
    data class Success(val modulos : List<ModuloUI>) : StudentHomeUIState
    object Error : StudentHomeUIState
    object Loading : StudentHomeUIState
}

class StudentHomeViewModel(token: String = "") : ViewModel() {
    private var retrofitService : StudentAPIService

    var studentHomeUIState: StudentHomeUIState by mutableStateOf(StudentHomeUIState.Loading)
        private set

    fun getModulos() {
        viewModelScope.launch {
            try{
                val resp = retrofitService.getModulos()
                val values = arrayOf(0,1,2,1)
                val n = resp.size
                var aux: MutableList<ModuloUI> = mutableListOf()
                for(i in 0..<n){
                    aux.add(ModuloUI(values[((i%4))],resp[i]))
                }
                studentHomeUIState = StudentHomeUIState.Success(modulos = aux)
            }catch (e: IOException){
                studentHomeUIState = StudentHomeUIState.Error
            }
        }
    }

    fun updateToken(newToken: String){
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", newToken)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        retrofitService = retrofit.create(StudentAPIService::class.java)
    }

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", token)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        retrofitService = retrofit.create(StudentAPIService::class.java)
    }
}