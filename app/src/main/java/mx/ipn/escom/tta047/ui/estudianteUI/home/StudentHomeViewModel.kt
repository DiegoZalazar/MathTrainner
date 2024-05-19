package mx.ipn.escom.tta047.ui.estudianteUI.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.ipn.escom.tta047.data.network.student.EjercicioGeneral
import mx.ipn.escom.tta047.data.network.student.Leccion
import mx.ipn.escom.tta047.data.network.student.Modulo
import mx.ipn.escom.tta047.data.network.student.Respuestas
import mx.ipn.escom.tta047.data.network.student.StudentAPIService
import mx.ipn.escom.tta047.ui.estudianteUI.exercises.ExercisesScreenViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

sealed interface LeccionUIState {
    data class Success(val leccion: Leccion): LeccionUIState
    object Error: LeccionUIState
    object Loading: LeccionUIState
    object NoContent: LeccionUIState
}

sealed interface EjerciciosUIState {
    data class Success(val ejercicios: List<EjercicioGeneral>, val nombreModulo: String): EjerciciosUIState
    object Error: EjerciciosUIState
    object Loading: EjerciciosUIState
    object NoContent: EjerciciosUIState
}

class StudentHomeViewModel(token: String = "") : ViewModel() {
    private var retrofitService : StudentAPIService

    var studentHomeUIState: StudentHomeUIState by mutableStateOf(StudentHomeUIState.Loading)
        private set
    var leccionUIState: LeccionUIState by mutableStateOf(LeccionUIState.Loading)
        private set
    var ejerciciosUIState: EjerciciosUIState by mutableStateOf(EjerciciosUIState.Loading)
        private set

    fun getModulos() {
        viewModelScope.launch {
            studentHomeUIState = try{
                Log.i("StudentHome", "Getting modules")
                val resp = retrofitService.getModulos()
                val values = arrayOf(0,1,2,1)
                val n = resp.size
                val aux: MutableList<ModuloUI> = mutableListOf()
                for(i in 0..<n){
                    aux.add(ModuloUI(values[((i%4))],resp[i]))
                }
                Log.i("StudentHome", "Getting modules done")
                StudentHomeUIState.Success(modulos = aux)
            }catch (e: IOException){
                Log.i("StudentHome", e.toString())
                StudentHomeUIState.Error
            }
        }
    }

    fun getLeccion(idModulo: Int){
        viewModelScope.launch {
            leccionUIState = LeccionUIState.Loading
            leccionUIState = try{
                val resp = retrofitService.getLeccion(idModulo)
                if(resp.isEmpty()){
                    LeccionUIState.NoContent
                } else {
                    Log.i("StudentHome", resp.toString())
                    LeccionUIState.Success(resp[0])
                }
            } catch (e: Exception){
                Log.i("StudentHome", "Error al obtener la leccion: $e")
                LeccionUIState.Error
            }
        }
    }

    fun sendRespuesta(modulo: Int, respuestas: Respuestas){
        viewModelScope.launch {
            try {
                Log.i("BugEndpoint", respuestas.respuestas.toString())
                val resp = retrofitService.postEjerciciosResultados(modulo,respuestas)
                Log.i("BugEndpoint", resp.toString())
            } catch (e: Exception) {
                Log.i("BugEndpoint", e.toString())

            }
        }
    }

    fun getEjerciciosAndUpdateExercisesVM(idModulo: Int, exercisesVM: ExercisesScreenViewModel, nombreModulo: String){
        viewModelScope.launch {
            ejerciciosUIState = EjerciciosUIState.Loading
            try {
                val resp = retrofitService.getEjerciciosModulo(idModulo)
                if (resp.isEmpty()){
                    Log.i("StudentHome", "no hay ejercicios")
                    ejerciciosUIState = EjerciciosUIState.NoContent
                } else {
                    Log.i("StudentHome", resp.toString())
                    ejerciciosUIState = EjerciciosUIState.Success(resp, nombreModulo)
                    exercisesVM.updateExercisesAndReset(resp, idModulo)
                    exercisesVM.updateSendRespuestas{ sendRespuesta(idModulo, it) }
                }
            } catch(e: Exception){
                Log.i("StudentHome", "Error al obtener los ejercicios: $e")
                ejerciciosUIState = EjerciciosUIState.Error
            }
        }
    }

    suspend fun borrarAvance(){
        retrofitService.borrarAvance()
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