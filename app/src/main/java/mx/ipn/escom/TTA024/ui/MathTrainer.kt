package mx.ipn.escom.TTA024.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.TTA024.ui.EstudianteUI.LoginScreen
import mx.ipn.escom.TTA024.ui.EstudianteUI.SignUpScreen

enum class MathTrainerNavScreens{
    Login,
    Home,
    Exercises
}

@Composable
fun MathTrainer(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val viewModel : MathTrainerViewModel = viewModel()
    var showSignIn by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()){
        NavHost(
            navController = navController,
            startDestination = MathTrainerNavScreens.Login.name
        ) {
            composable(route = MathTrainerNavScreens.Login.name){
                Login(
                    signIn = viewModel::signIn,
                    showSignIn = showSignIn
                ) {
                    showSignIn = it
                }
            }
            composable(route = MathTrainerNavScreens.Home.name){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if(viewModel.isSignedIn){
                        Text(text = "Sesion iniciada")
                    }
                    Button(onClick = { navController.navigate(MathTrainerNavScreens.Login.name) }) {
                        Text("Cerrar Sesion")
                    }
                }
            }
        }
    }




}

@Composable
fun Login(
    signIn: (String, String) -> Unit,
    showSignIn: Boolean,
    changeShowSignIn: (Boolean) -> Unit
){
    if(showSignIn){
        LoginScreen(
            navigateToHome = signIn,
            navigateToSignUp = { changeShowSignIn(false) }
        )
    }else{
        SignUpScreen(
            navigateToSignIn = { changeShowSignIn(true) }
        )
    }
}

@Composable
fun StarExercisesScreen(
    navigateToExercises: ()->Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = navigateToExercises
        ){
            Text("Iniciar Ejercicios")
        }
    }
}