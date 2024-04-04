package mx.ipn.escom.TTA024.AdminUI

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.data.models.Ejercicio

@Composable
fun AdminEditEjercicioComposable(navController: NavController, ejercicio: Ejercicio){
    Text(text = "ejercicio: "+ejercicio.planteamientoEjercicio)
}