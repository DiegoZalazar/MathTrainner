package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.fillblank

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private val ejemlpo = FillBlank(
    instrucciones = "Completa el cambio de variable en el siguiente procedimiento",
    latex = "\\int{\\frac{1}{x-1}dx}, u=\\_\\_\\_, du=dx \\\\ \\int{\\frac{1}{u}du} \\\\ =ln(u)+C \\\\ =ln(x-1)+C",
    respuesta = "x-1"
)

class ExerciseFillBlankViewModel(
    val ejercicio: FillBlank = ejemlpo
): ViewModel() {

    private val _uiState = MutableStateFlow(ejercicio)
    val uiState: StateFlow<FillBlank> = _uiState.asStateFlow()
    var correcto : Boolean by mutableStateOf(false)
    var resuelto : Boolean by mutableStateOf(false)
    var respuesta by mutableStateOf("")

    fun addToRespuesta(char: String){
        respuesta += char
    }

    fun dropFromRespuesta(){
        respuesta = respuesta.dropLast(1)
    }

    fun comprobar(){
        resuelto = true
        if(respuesta.equals(ejercicio.respuesta)){
            correcto = true
        }else{
            correcto = false
        }
    }

    fun reset(){
        correcto = false
        resuelto = false
        respuesta = ""
    }

    init {
        reset()
    }
}