package mx.ipn.escom.tta047.ui.estudianteUI.exercises.columns

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import mx.ipn.escom.tta047.ui.smallcomponents.ButtonState

private val ejemplo = Columns(
    instrucciones = "Relaciona las integrales con su resultado:",
    pares = listOf(
        Pair("\\int{adu}","a\\int{du}"),
        Pair("\\int{u^ndu}","\\frac{u^n+1}{n+1}+C"),
        Pair("\\int{dx}","x+C"),
        Pair("\\int{\\frac{du}{u}}","ln(u)+C")
    )
)

data class Opcion(
    val valor: String,
    val estado: ButtonState
)

class ExerciseColumnsViewModel(
    private val exerciseColumns : Columns = ejemplo
) : ViewModel() {
    var opcionesDerecha by mutableStateOf(listOf<Opcion>())
    var opcionesIzquierda by mutableStateOf(listOf<Opcion>())
    private var seleccionadoDerecha = ""
    private var seleccionadoIzquierda = ""
    private var resueltos = 0
    var incorrecto by mutableStateOf(false)
    var correcto by mutableStateOf(false)


    private fun shuffleOpciones(){
        val l = exerciseColumns.pares
        val keys = l.map{ it.first }.shuffled()
        val values = l.map{ it.second }.shuffled()

        val aux = mutableListOf<Opcion>()
        for(key in keys){
            aux += Opcion(key,ButtonState.ENABLED)
        }
        opcionesDerecha = aux

        val aux2 = mutableListOf<Opcion>()
        for(v in values){
            aux2 += Opcion(v,ButtonState.ENABLED)
        }
        opcionesIzquierda = aux2
    }

    fun seleccionar(derecha: Boolean, opcion: String){
        var estado = ButtonState.ACTIVE
        val aux = mutableListOf<Opcion>()

        if(derecha){
            if(seleccionadoDerecha == opcion){
                seleccionadoDerecha = ""
                estado = ButtonState.ENABLED
            } else {
                seleccionadoDerecha = opcion
            }
            for(par in opcionesDerecha){
                aux += if(par.valor == opcion){
                    Opcion(par.valor,estado)
                }else{
                    Opcion(par.valor,if(par.estado == ButtonState.ACTIVE) ButtonState.ENABLED else par.estado)
                }
            }
            opcionesDerecha = aux
        } else {
            if(seleccionadoIzquierda == opcion){
                seleccionadoIzquierda = ""
                estado = ButtonState.ENABLED
            } else {
                seleccionadoIzquierda = opcion
            }
            for(par in opcionesIzquierda){
                aux += if(par.valor == opcion){
                    Opcion(par.valor,estado)
                }else{
                    Opcion(par.valor,if(par.estado == ButtonState.ACTIVE) ButtonState.ENABLED else par.estado)
                }
            }
            opcionesIzquierda = aux
        }

        check()
    }

    private fun check() {
        if(seleccionadoDerecha.isNotEmpty() and seleccionadoIzquierda.isNotEmpty()){
            for(par in exerciseColumns.pares){
                if(par.first == seleccionadoDerecha){
                    val estado = if(par.second == seleccionadoIzquierda) ButtonState.RIGHT else ButtonState.WRONG
                    incorrecto = par.second != seleccionadoIzquierda
                    // correcto
                    val newOpcDer = mutableListOf<Opcion>()
                    for(opc in opcionesDerecha){
                        newOpcDer += if(opc.valor == seleccionadoDerecha){
                            Opcion(opc.valor, estado)
                        } else {
                            Opcion(opc.valor, opc.estado)
                        }
                    }
                    opcionesDerecha = newOpcDer

                    val newOpcIzq = mutableListOf<Opcion>()
                    for(opc in opcionesIzquierda){
                        newOpcIzq += if(opc.valor == seleccionadoIzquierda){
                            Opcion(opc.valor, estado)
                        } else {
                            Opcion(opc.valor, opc.estado)
                        }
                    }
                    opcionesIzquierda = newOpcIzq

                    seleccionadoDerecha = ""
                    seleccionadoIzquierda = ""

                    resueltos ++
                    if(resueltos == exerciseColumns.pares.size && !incorrecto){
                        correcto = true
                    }
                }
            }
        }

    }

    fun reset(){
        shuffleOpciones()
        seleccionadoDerecha = ""
        seleccionadoIzquierda = ""
        resueltos = 0
        incorrecto = false
        correcto = false
    }

    init{
        reset()
    }
}