package mx.ipn.escom.TTA024.domain.model

data class Ejercicio(val idEjercicio: Int,
                          var planteamientoEjercicio: String,
                          var resolucionEjercicio: String,
                          var tiempoEjercicio: Int,
                          var tipoEjercicio: String,
                          var nivelEjercicio: Int){
}

