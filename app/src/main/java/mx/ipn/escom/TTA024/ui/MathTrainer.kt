package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.launch
import mx.ipn.escom.TTA024.ui.EstudianteUI.SignInScreen
import mx.ipn.escom.TTA024.ui.EstudianteUI.SignUpScreen
import mx.ipn.escom.TTA024.ui.EstudianteUI.VerifyEmailScreen

enum class MathTrainerNavScreens{
    SplashScreen,
    SignIn,
    SignUp,
    VerifyEmail,
    AppHome,
    Exercises
}

@Composable
fun MathTrainer(
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val loginViewModel: LoginViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()){
        NavHost(
            navController = navController,
            startDestination = MathTrainerNavScreens.SplashScreen.name
        ) {
            navigation(
                route = "login",
                startDestination = MathTrainerNavScreens.SignIn.name
            ) {
                composable(route = MathTrainerNavScreens.SignIn.name){
                    SignInScreen(navController, loginViewModel)
                }
                composable(route = MathTrainerNavScreens.SignUp.name){
                    SignUpScreen(navController)
                }

                composable(route = "${MathTrainerNavScreens.VerifyEmail.name}/{email}", arguments = listOf(navArgument(name = "email") {type = NavType.StringType})){
                    backStackEntry -> 
                    VerifyEmailScreen(navController = navController, email = backStackEntry.arguments?.getString("email")?: "")
                }
            }

            navigation(
                route = "home",
                startDestination = MathTrainerNavScreens.AppHome.name
            ) {
                composable(route = MathTrainerNavScreens.AppHome.name){
                    Home(
                        navToLogin = {
                            navController.navigate("login"){
                                popUpTo("home")
                            }
                        },
                        isSignedIn = true
                    )
                }
            }

            composable(MathTrainerNavScreens.SplashScreen.name){
                MathTrainerSplashScreen(
                    navToHome = {
                        Log.i("Amplify", "navigating to home")
                        navController.navigate("home")
                    },
                    navToLogin = {
                        Log.i("Amplify", "navigating to login")
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}

@Composable
fun Home(
    navToLogin: () -> Unit,
    isSignedIn: Boolean
) {
    val scope = rememberCoroutineScope()
    var user by remember {
        mutableStateOf("")
    }
    var closeSesionLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        try {
            val session = Amplify.Auth.fetchAuthSession()
            Log.i("AmplifyQuickstart", "Auth session = $session")
            if(session.isSignedIn){
                user = Amplify.Auth.getCurrentUser().username
            }
        } catch (error: AuthException) {
            Log.e("AmplifyQuickstart", "Failed to fetch auth session", error)
            navToLogin()
        }

        try {
            val attributes = Amplify.Auth.fetchUserAttributes()
            Log.i("AuthDemo", "User attributes = $attributes")
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to fetch user attributes", error)
        }

        try {
            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            val id = session.identityIdResult
            if (id.type == AuthSessionResult.Type.SUCCESS) {
                Log.i("AuthQuickStart", "IdentityId: ${id.value}")
            } else if (id.type == AuthSessionResult.Type.FAILURE) {
                Log.i("AuthQuickStart", "IdentityId not present: ${id.error}")
            }
        } catch (error: AuthException) {
            Log.e("AuthQuickStart", "Failed to fetch session", error)
        }
    }
    if(closeSesionLoading){
        LaunchedEffect(key1 = true) {
            try {
                Amplify.Auth.signOut()
                navToLogin()
            } catch (error: AuthException) {
                Log.e("AmplifyQuickstart", "Failed to sign out auth session", error)
                navToLogin()
            }
            closeSesionLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(isSignedIn){
            Text(text = "Sesion iniciada")
            Text("Bienvenido ${user}")
        }
        Button(onClick = { closeSesionLoading = true }) {
            Text("Cerrar Sesion")
        }
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