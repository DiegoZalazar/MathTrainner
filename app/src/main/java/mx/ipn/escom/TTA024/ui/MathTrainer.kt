package mx.ipn.escom.TTA024.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.kotlin.core.Amplify
import com.google.gson.Gson
import io.github.nefilim.kjwt.JWSRSA256Algorithm
import io.github.nefilim.kjwt.JWT
import mx.ipn.escom.TTA024.EjerciciosAdminComposable
import mx.ipn.escom.TTA024.LeccionesAdminComposable
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.domain.model.Usuario
import mx.ipn.escom.TTA024.ui.AdminUI.AdminFormEjercicioComposable
import mx.ipn.escom.TTA024.ui.AdminUI.AdminFormLeccionComposable
import mx.ipn.escom.TTA024.ui.AdminUI.EditModulo
import mx.ipn.escom.TTA024.ui.AdminUI.EditUserComposable
import mx.ipn.escom.TTA024.ui.AdminUI.ModulosAdminComposable
import mx.ipn.escom.TTA024.ui.AdminUI.PrincipalAdministrador
import mx.ipn.escom.TTA024.ui.AdminUI.UsuariosComposable
import mx.ipn.escom.TTA024.ui.AuthUI.ForgotPswdScreen
import mx.ipn.escom.TTA024.ui.AuthUI.LoginViewModel
import mx.ipn.escom.TTA024.ui.AuthUI.ResetPasswordScreen
import mx.ipn.escom.TTA024.ui.AuthUI.SignInScreen
import mx.ipn.escom.TTA024.ui.AuthUI.SignUpScreen
import mx.ipn.escom.TTA024.ui.AuthUI.VerifyCodeScreen
import mx.ipn.escom.TTA024.ui.EstudianteUI.Lesson
import mx.ipn.escom.TTA024.ui.EstudianteUI.estudianteconfig.EstudianteConfig
import mx.ipn.escom.TTA024.ui.EstudianteUI.estudianteconfig.EstudianteConfigNombre
import mx.ipn.escom.TTA024.ui.EstudianteUI.estudianteconfig.EstudianteConfigUIState
import mx.ipn.escom.TTA024.ui.EstudianteUI.estudianteconfig.EstudianteConfigVM
import mx.ipn.escom.TTA024.ui.EstudianteUI.home.StudentHome
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.ExerciseNavScreens
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.ExercisesScreen
import mx.ipn.escom.TTA024.ui.EstudianteUI.exercises.ExercisesScreenViewModel
import mx.ipn.escom.TTA024.ui.EstudianteUI.home.StudentHomeViewModel
import mx.ipn.escom.TTA024.ui.navigation.AppScreens
import mx.ipn.escom.TTA024.ui.viewmodels.AdminEjerciciosViewModel
import mx.ipn.escom.TTA024.ui.viewmodels.AdminLeccionesViewModel
import mx.ipn.escom.TTA024.ui.viewmodels.AdminUsuariosViewModel
import mx.ipn.escom.TTA024.ui.viewmodels.ModulosAdminViewModel

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
    StudentHome,
    Leccion,
    StudentConfig,
    StudentConfigName,
    StudentConfigResetPswd
}

@Composable
fun MathTrainer(
    navController: NavHostController = rememberNavController(),
    modulosAdminViewModel: ModulosAdminViewModel,
    adminLeccionesViewModel: AdminLeccionesViewModel,
    adminEjerciciosViewModel: AdminEjerciciosViewModel,
    studentHomeViewModel: StudentHomeViewModel,
    adminUsuariosViewModel: AdminUsuariosViewModel,
    estudianteConfigVM: EstudianteConfigVM
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val loginViewModel: LoginViewModel = viewModel()
    val viewModelExercises : ExercisesScreenViewModel = viewModel()
    val uiState by viewModelExercises.uiState.collectAsState()

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
                            popUpTo(LoginScreens.SplashScreen.name) { inclusive = true }
                        }
                    },
                    navToLogin = {
                        Log.i("Amplify", "navigating to login")
                        navController.navigate("login"){
                            popUpTo(LoginScreens.SplashScreen.name) { inclusive = true }
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
                    ResetPasswordScreen(
                        email = backStackEntry.arguments?.getString("email")?: "",
                        navigateNextScreen = { navController.popBackStack(LoginScreens.SignIn.name, true) }
                    )
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
                    StudentHome(
                        studentVM = studentHomeViewModel,
                        exercisesScreenViewModel = viewModelExercises,
                        navController = navController,
                        updateConfigView = { estudianteConfigVM.reload() }
                    )
                }
                composable(route = ExerciseNavScreens.Exercises.name){
                    ExercisesScreen(
                        exercisesUIState = uiState,
                        nextAction = viewModelExercises::nextExercise,
                        cancelAction = {
                            viewModelExercises.reset()
                            navController.popBackStack(StudentScreens.StudentHome.name, inclusive = false)
                        })
                }

                composable(route = StudentScreens.Leccion.name){
                    Lesson(
                        studentHomeViewModel = studentHomeViewModel,
                        navController = navController
                    )
                }

                composable(route = StudentScreens.StudentConfig.name){
                    EstudianteConfig(navController = navController)
                }

                composable(route = StudentScreens.StudentConfigName.name){
                    EstudianteConfigNombre(navController)
                }

                composable(route = StudentScreens.StudentConfigResetPswd.name){
                    ResetPasswordScreen(
                        email = if (estudianteConfigVM.estudianteConfigUIState is EstudianteConfigUIState.Success)
                                (estudianteConfigVM.estudianteConfigUIState as EstudianteConfigUIState.Success).email else "",
                        navigateNextScreen = { navController.navigateUp() }
                    )
                }
            }

            navigation(
                route = "admin",
                startDestination = AppScreens.AdminPrincipalActivity.route
            ){
                composable(route = AppScreens.AdminPrincipalActivity.route) {
                    PrincipalAdministrador(navController)
                }
                composable(route = AppScreens.AdminEditModActivity.route+"/{modulo}",
                    arguments = listOf(navArgument(name = "modulo") {
                        type = NavType.StringType
                    })) {
                        backStackEntry ->
                    backStackEntry?.arguments?.getString("modulo")?.let { json ->
                        val modulo = Gson().fromJson(json, ModuloModel::class.java)
                        EditModulo(navController,modulo)
                    }
                }
                composable(
                    route = AppScreens.AdminEditUserActivity.route + "/{user}",
                    arguments = listOf(navArgument(name = "user") {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    backStackEntry?.arguments?.getString("user")?.let { json ->
                        val user = Gson().fromJson(json, Usuario::class.java)
                        EditUserComposable(navController, user,adminUsuariosViewModel)
                    }
                }
                composable(route = AppScreens.AdminLeccionesActivity.route+"/{modulo}",arguments = listOf(navArgument(name = "modulo") {
                    type = NavType.StringType
                })) {
                        backStackEntry ->
                    backStackEntry?.arguments?.getString("modulo")?.let { json ->
                        val modulo = Gson().fromJson(json, Modulo::class.java)
                        LeccionesAdminComposable(navController,modulo,adminLeccionesViewModel)
                    }
                }
                composable(route = AppScreens.AdminEjerciciosActivity.route+"/{modulo}",arguments = listOf(navArgument(name = "modulo") {
                    type = NavType.StringType
                })) {
                        backStackEntry ->
                    backStackEntry?.arguments?.getString("modulo")?.let { json ->
                        val modulo = Gson().fromJson(json, Modulo::class.java)
                        EjerciciosAdminComposable(navController,modulo,adminEjerciciosViewModel)
                    }
                }

                composable(route = AppScreens.AdminFormEjercicioActivity.route+"/{modulo}/{ejercicio}",arguments = listOf(
                    navArgument(name = "ejercicio"){ type = NavType.StringType },
                    navArgument(name="modulo"){type = NavType.StringType}
                )
                ) {
                        backStackEntry ->

                    val ejercicio = Gson().fromJson(backStackEntry.arguments?.getString("ejercicio"), Ejercicio::class.java)
                    val modulo = Gson().fromJson(backStackEntry.arguments?.getString("modulo"), Modulo::class.java)

                    AdminFormEjercicioComposable(navController = navController,modulo=modulo , ejercicio = ejercicio,adminEjerciciosViewModel)

                }

                composable(route = AppScreens.AdminFormLeccActivity.route+"/{modulo}/{leccion}",arguments = listOf(
                    navArgument(name = "leccion"){ type = NavType.StringType },
                    navArgument(name="modulo"){type = NavType.StringType}
                )
                ) {
                        backStackEntry ->

                    val leccion = Gson().fromJson(backStackEntry.arguments?.getString("leccion"), Leccion::class.java)
                    val modulo = Gson().fromJson(backStackEntry.arguments?.getString("modulo"), Modulo::class.java)

                    AdminFormLeccionComposable(navController = navController,modulo=modulo ,leccion = leccion,adminLeccionesViewModel)

                }

                composable(route = AppScreens.AdminModulosActivity.route) {
                    ModulosAdminComposable(navController,modulosAdminViewModel)
                }
                composable(route = AppScreens.AdminUsuariosActivity.route) {
                    UsuariosComposable(navController,adminUsuariosViewModel)
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
fun Home(
    navController: NavController
){
    LaunchedEffect(key1 = true) {
        var isAdmin = false
        try {
            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            val tokenTest = session.tokensResult.value?.idToken?: "no hay token"
            val token = session.tokensResult.value
//            Log.i("Token JWT", token?.accessToken?: "no se pudo obtener el token")
            Log.i("Token JWT", tokenTest)
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