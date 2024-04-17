package mx.ipn.escom.TTA024.ui.AdminUI

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.ipn.escom.TTA024.data.models.EjercicioModel

@Composable
fun AdminEditEjercicioComposable(navController: NavController, ejercicio: EjercicioModel){
    Text(text = "ejercicio: "+ejercicio.planteamientoEjercicio)
}