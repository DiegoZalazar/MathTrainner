package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.columns.Columns
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.fillblank.FillBlank
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc.MultOpc

sealed interface ExerciseUIState {
    data class ExerciseUIStateColumns(val exerciseColumns: Columns) : ExerciseUIState
    data class ExerciseUIStateMultOpc(val exerciseOptions: MultOpc) : ExerciseUIState
    data class ExerciseUIStateFillBlank(val exerciseFillBlank: FillBlank) : ExerciseUIState
    object FinishedExercises: ExerciseUIState
}

private val ejercicios: List<ExerciseUIState> = listOf(
    ExerciseUIState.ExerciseUIStateColumns(
        Columns(
            instrucciones = "Relaciona las integrales con su resultado:",
            pares = listOf(
                Pair("\\int{adu}","a\\int{du}"),
                Pair("\\int{u^ndu}","\\frac{u^n+1}{n+1}+C"),
                Pair("\\int{dx}","x+C"),
                Pair("\\int{\\frac{du}{u}}","ln(u)+C")
            )
        )
    ),
    ExerciseUIState.ExerciseUIStateFillBlank(
        FillBlank(
            instrucciones = "Completa el cambio de variable en el siguiente procedimiento",
            latex = "\\int{\\frac{1}{x-1}dx}, u=\\_\\_\\_, du=dx \\\\ \\int{\\frac{1}{u}du} \\\\ =ln(u)+C \\\\ =ln(x-1)+C",
            respuesta = "x-1"
        )
    ),
    ExerciseUIState.ExerciseUIStateMultOpc(
        MultOpc(
            instrucciones = "Selecciona los valores correctos de u y du para hacer un cambio de variable en la siguiente integral:",
            cuerpo = "\\int{\\frac{6x+2}{3x^2+2x}dx}",
            respCorrecta = "u=3x^2+2x,du=6x+2",
            opciones = listOf(
                "u=6x+2,du=6",
                "u=6x,du=6",
                "u=3x^2+2x,du=x+1"
            )
        )
    )
)

class ExercisesScreenViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(ExercisesUIState())
    val uiState: StateFlow<ExercisesUIState> = _uiState.asStateFlow()

    private var tiempos: MutableList<Int> = mutableListOf()
    private var currTiempo: Int = 0

    fun nextExercise(correct: Boolean, nextTiempo: Int) {
        tiempos[_uiState.value.currIndex] = nextTiempo - currTiempo
        currTiempo = nextTiempo
        val correctExercises = if(correct) _uiState.value.correctExercises + 1 else _uiState.value.correctExercises
        val nextIndex = _uiState.value.currIndex + 1
        val totalExercises = _uiState.value.exercises.size
        val progress = (nextIndex.toFloat()/totalExercises)
        if(nextIndex == totalExercises){
            _uiState.update { currExerciseUIState ->
                currExerciseUIState.copy(
                    correctExercises = correctExercises,
                    currIndex = nextIndex,
                    progress = progress,
                    finished = true,
                    currExerciseUIState = ExerciseUIState.FinishedExercises,
                    running = false
                )
            }
            Log.i("Timer", tiempos.toString())
        } else{
            val nextExerciseUIState = _uiState.value.exercises[nextIndex]
            _uiState.update { currExerciseUIState ->
                currExerciseUIState.copy(
                    correctExercises = correctExercises,
                    currIndex = nextIndex,
                    progress = progress,
                    finished = false,
                    currExerciseUIState = nextExerciseUIState
                )
            }
        }
    }

    fun initSesion(){
        _uiState.update { currExercisesState ->
            currExercisesState.copy(
                running = true
            )
        }
    }

    fun pauseSesion(){
        _uiState.update { currExercisesUIState ->
            currExercisesUIState.copy(
                running = false
            )
        }
    }

    fun reset() {
        _uiState.value = ExercisesUIState()
        _uiState.update { currExerciseUIState ->
            currExerciseUIState.copy(
                exercises = ejercicios,
                currExerciseUIState = ejercicios[0]
            )
        }
        tiempos = MutableList(ejercicios.size) { 0 }
        currTiempo = 0
    }

    init {
        reset()
    }
}