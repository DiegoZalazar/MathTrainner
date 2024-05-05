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
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.kotlin.core.Amplify
import io.github.nefilim.kjwt.JWSRSA256Algorithm
import io.github.nefilim.kjwt.JWT
import mx.ipn.escom.TTA024.ui.AuthUI.ForgotPswdScreen
import mx.ipn.escom.TTA024.ui.AuthUI.ResetPasswordScreen
import mx.ipn.escom.TTA024.ui.AuthUI.SignInScreen
import mx.ipn.escom.TTA024.ui.AuthUI.SignUpScreen
import mx.ipn.escom.TTA024.ui.AuthUI.VerifyCodeScreen

enum class LoginScreens{
    SplashScreen,
    SignIn,
    SignUp,
    VerifyCode,
    AppHome,
    ForgotPassword,
    ResetPassword
}

enum class StudentScreens {
    StudentHome
}

enum class AdminScreens {
    AdminHome
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
            startDestination = LoginScreens.SplashScreen.name
        ) {
            composable(LoginScreens.SplashScreen.name){
                MathTrainerSplashScreen(
                    navToHome = {
                        Log.i("Amplify", "navigating to home")
                        navController.navigate("home"){
                            popUpTo(LoginScreens.SplashScreen.name)
                        }
                    },
                    navToLogin = {
                        Log.i("Amplify", "navigating to login")
                        navController.navigate("login"){
                            popUpTo(LoginScreens.SplashScreen.name)
                        }
                    }
                )
            }

            navigation(
                route = "login",
                startDestination = LoginScreens.SignIn.name
            ) {
                composable(route = LoginScreens.SignIn.name){
                    SignInScreen(navController, loginViewModel)
                }
                composable(route = LoginScreens.SignUp.name){
                    SignUpScreen(navController)
                }
                composable(route = "${LoginScreens.VerifyCode.name}/{email}",
                    arguments = listOf(navArgument(name = "email") {type = NavType.StringType})
                ){
                    backStackEntry -> 
                        VerifyCodeScreen(navController = navController, email = backStackEntry.arguments?.getString("email")?: "")
                }
                composable(route = LoginScreens.ForgotPassword.name){
                    ForgotPswdScreen(navController = navController)
                }
                composable(route = "${LoginScreens.ResetPassword.name}/{email}",
                    arguments = listOf(navArgument(name = "email") {type = NavType.StringType})
                ){
                    backStackEntry ->
                        ResetPasswordScreen(navController = navController, email = backStackEntry.arguments?.getString("email")?: "")
                }
            }

            navigation(
                route = "home",
                startDestination = LoginScreens.AppHome.name
            ) {
                composable(route = LoginScreens.AppHome.name){
                    Home(navController)
                }
            }

            navigation(
                route = "student",
                startDestination = StudentScreens.StudentHome.name
            ){
                composable(route = StudentScreens.StudentHome.name){
                    StudentHome(navController = navController)
                }
            }

            navigation(
                route = "admin",
                startDestination = AdminScreens.AdminHome.name
            ){
                composable(route = AdminScreens.AdminHome.name){
                    AdminHome(navController = navController)
                }
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
fun StudentHome(
    navController: NavController
) {
    var userName by remember { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        try {
            val attributes = Amplify.Auth.fetchUserAttributes()
            val name = attributes.find { it.key == AuthUserAttributeKey.name() }
            Log.i("AuthDemo", "User attributes = $attributes")
            Log.i("AuthDemo", name?.value?:"no se pudo obtener el nombre")
            userName = name?.value?:"no se pudo obtener el nombre"
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to fetch user attributes", error)
        }
    }
    
    var closeSesionLoading by remember { mutableStateOf(false) }
    if(closeSesionLoading){
        LaunchedEffect(key1 = true) {
            try {
                Amplify.Auth.signOut()
            } catch (error: AuthException) {
                Log.e("AmplifyQuickstart", "Failed to sign out auth session", error)
            }
            navController.navigate("login"){
                popUpTo("student")
            }
            closeSesionLoading = false
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Sesion iniciada")
        Text("Bienvenido estudiante")
        Text("tu nombre: ${userName}")

        Button(onClick = {
            closeSesionLoading = true
        }) {
            Text("Cerrar Sesion")
        }
    }
}

@Composable
fun AdminHome(
    navController: NavController
) {
    var userName by remember { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        try {
            val attributes = Amplify.Auth.fetchUserAttributes()
            val name = attributes.find { it.key == AuthUserAttributeKey.name() }
            Log.i("AuthDemo", "User attributes = $attributes")
            Log.i("AuthDemo", name?.value?:"no se pudo obtener el nombre")
            userName = name?.value?:"no se pudo obtener el nombre"
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to fetch user attributes", error)
        }
    }

    var closeSesionLoading by remember { mutableStateOf(false) }
    if(closeSesionLoading){
        LaunchedEffect(key1 = true) {
            try {
                Amplify.Auth.signOut()
            } catch (error: AuthException) {
                Log.e("AmplifyQuickstart", "Failed to sign out auth session", error)
            }
            navController.navigate("login"){
                popUpTo("admin")
            }
            closeSesionLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Sesion iniciada")
        Text("Bienvenido admin")
        Text("tu nombre: ${userName}")

        Button(onClick = {
            closeSesionLoading = true
        }) {
            Text("Cerrar Sesion")
        }
    }
}

@Composable 
fun Home(
    navController: NavController
){
    LaunchedEffect(key1 = true) {
        var isAdmin = false
        try {
            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            val token = session.tokensResult.value
            Log.i("Token JWT", token?.accessToken?: "no se pudo obtener el token")
            var body = ""
            var groups = listOf("")
            JWT.decodeT(token?.accessToken?:"", JWSRSA256Algorithm).tap {
                body = it.claimValue("scope").orNull()?: "no se pudo obtener el valor"
                groups = it.claimValueAsList("cognito:groups")
            }
            Log.i("Token JWT", JWT.decodeT(token?.accessToken?:"", JWSRSA256Algorithm).toString())
            Log.i("Token JWT", body)
            if(groups.isEmpty()){
                Log.i("Token JWT", "No hay groups para este usuario")
            }else{
                for(g in groups){
                    if(g == "Admins"){
                        isAdmin = true
                        break
                    }
                    Log.i("Token JWT", g)
                }
            }
        } catch (error: AuthException) {
            Log.e("AuthQuickStart", "Failed to fetch session", error)
        }
        if(isAdmin){
            navController.navigate("admin") {
                popUpTo("home")
            }
        }else{
            navController.navigate("student"){
                popUpTo("home")
            }
        }
    }
}

/*
@Composable
fun Home(
    navToLogin: () -> Unit,
    navToAdmin: () -> Unit,
    navToStudent: () -> Unit,
    isSignedIn: Boolean
) {
    val scope = rememberCoroutineScope()
    var user by remember {
        mutableStateOf("")
    }
    var isAdmin by remember { mutableStateOf(false) }
    var closeSesionLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        // fetch sesion
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

        // attibutes y obtener el nombre
        try {
            val attributes = Amplify.Auth.fetchUserAttributes()
            val name = attributes.find { it.key == AuthUserAttributeKey.name() }
            Log.i("AuthDemo", "User attributes = $attributes")
            Log.i("AuthDemo", name?.value?:"no se pudo obtener el nombre")
            user = name?.value?:"no se pudo obtener el nombre"
        } catch (error: AuthException) {
            Log.e("AuthDemo", "Failed to fetch user attributes", error)
        }

        // tokens
        try {
            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            val token = session.tokensResult.value
            val token2 = session.userPoolTokensResult.value.toString()
            val x = JWT.decodeT(token2, JWSRSA256Algorithm).tap {
                Log.i("JWT Test", it.signedData())
            }
            val id = session.identityIdResult
            if (id.type == AuthSessionResult.Type.SUCCESS) {
                Log.i("AuthQuickStart", "IdentityId: ${id.value}")
            } else if (id.type == AuthSessionResult.Type.FAILURE) {
                Log.i("AuthQuickStart", "IdentityId not present: ${id.error}")
            }
            Log.i("Token", token?.accessToken?: "no se pudo obtener el token")
            Log.i("Token", token2)
            var body = ""
            var groups = listOf("")
            JWT.decodeT(token?.accessToken?:"", JWSRSA256Algorithm).tap {
                body = it.claimValue("scope").orNull()?: "no se pudo obtener el valor"
                groups = it.claimValueAsList("cognito:groups")
            }
            Log.i("Token JWT", JWT.decodeT(token?.accessToken?:"", JWSRSA256Algorithm).toString())
            Log.i("Token JWT", body)
            if(groups.isEmpty()){
                Log.i("Token JWT", "No hay groups para este usuario")
            }else{
                for(g in groups){
                    if(g == "Admins"){
                        isAdmin = true
                        break
                    }
                    Log.i("Token JWT", g)
                }
            }
        } catch (error: AuthException) {
            Log.e("AuthQuickStart", "Failed to fetch session", error)
        }
        if(isAdmin){
            navToAdmin()
        }else{
            navToStudent()
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
*/


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