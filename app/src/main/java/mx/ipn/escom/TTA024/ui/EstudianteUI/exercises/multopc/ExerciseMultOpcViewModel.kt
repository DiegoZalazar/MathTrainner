package mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.multopc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.ipn.escom.TTA024.ui.smallcomponents.ButtonState

private val ejemplo = MultOpc(
    instrucciones = "Selecciona los valores correctos de u y du para hacer un cambio de variable en la siguiente integral:",
    cuerpo = "\\int{\\frac{6x+2}{3x^2+2x}dx}",
    respCorrecta = "u=3x^2+2x,du=6x+2",
    opciones = listOf(
        "u=6x+2,du=6",
        "u=6x,du=6",
        "u=3x^2+2x,du=x+1"
    )
)

class ExerciseMultOpcViewModel(
    val ejercicio: MultOpc = ejemplo
) : ViewModel() {
    var opciones = shuffleOptions(ejercicio.opciones + ejemplo.respCorrecta)
    private val _uiState = MutableStateFlow(MultOpcUIState(
        instrucciones = ejemplo.instrucciones,
        cuerpo = ejemplo.cuerpo,
        respCorrecta = ejemplo.respCorrecta,
        opciones = opciones,
        error = false,
        correcto = false
    ))
    val uiState: StateFlow<MultOpcUIState> = _uiState.asStateFlow()

    var seleccionada: String by mutableStateOf("")
        private set

    var buttonStates: List<ButtonState> by mutableStateOf(
        List(4){ ButtonState.ENABLED}
    )

    fun shuffleOptions(options: List<String>): List<String>{
        // Se clona la lista original para no modificarla directamente
        val shuffledList = options.toMutableList()
        // Se utiliza la función shuffle para ordenar aleatoriamente la lista
        shuffledList.shuffle()
        return shuffledList
    }

    fun changeSelection(opc: Int){
        seleccionada = uiState.value.opciones[opc]
        buttonStates = buttonStates.map { ButtonState.ENABLED }
        buttonStates = buttonStates.toMutableList().apply{ this[opc] = ButtonState.ACTIVE }
    }

    fun checkAnswer(){
        val respCorrecta = uiState.value.respCorrecta
        if(seleccionada.equals(respCorrecta)){
            buttonStates = buttonStates.map { it -> if(it == ButtonState.ACTIVE) ButtonState.RIGHT else it }
            _uiState.update { currentState ->
                currentState.copy(
                    correcto = true,
                    error = false
                )
            }
        }else {
            buttonStates = buttonStates.map { it -> if(it == ButtonState.ACTIVE) ButtonState.WRONG else it }
            _uiState.update { currentState ->
                currentState.copy(
                    correcto = false,
                    error = true
                )
            }
        }
    }

    fun reset(){
        opciones = shuffleOptions(ejercicio.opciones + ejercicio.respCorrecta)
        _uiState.value = MultOpcUIState(
            instrucciones = ejemplo.instrucciones,
            cuerpo = ejemplo.cuerpo,
            respCorrecta = ejemplo.respCorrecta,
            opciones = opciones,
            error = false,
            correcto = false
        )
        seleccionada = ""
        buttonStates = List(4){ ButtonState.ENABLED}
    }

    init {
        reset()
    }
}