package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amplifyframework.core.Amplify

@Composable
fun MathTrainer(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val viewModel : MathTrainerViewModel = viewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = if(viewModel.isSignedIn) "Sesion iniciada" else "Sesion no iniciada")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.closeSesion() }
        ){
            Text("Cerrar Sesion")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.initSesion() }
        ){
            Text("Iniciar Sesion ")
        }
    }
}