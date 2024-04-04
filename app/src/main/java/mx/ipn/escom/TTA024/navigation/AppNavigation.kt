package mx.ipn.escom.TTA024.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import mx.ipn.escom.TTA024.AdminUI.AdminEditEjercicioComposable
import mx.ipn.escom.TTA024.AdminUI.AdminEditLeccionComposable
import mx.ipn.escom.TTA024.AdminUI.EditModulo
import mx.ipn.escom.TTA024.AdminUI.EditUserComposable
import mx.ipn.escom.TTA024.AdminUI.ModulosAdminComposable
import mx.ipn.escom.TTA024.AdminUI.PrincipalAdministrador
import mx.ipn.escom.TTA024.AdminUI.UsuariosComposable
import mx.ipn.escom.TTA024.EjerciciosAdminComposable
import mx.ipn.escom.TTA024.LeccionesAdminComposable
import mx.ipn.escom.TTA024.data.models.Ejercicio
import mx.ipn.escom.TTA024.data.models.Estudiante
import mx.ipn.escom.TTA024.data.models.Leccion
import mx.ipn.escom.TTA024.data.models.Modulo

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.AdminPrincipalActivity.route
    ) {
        composable(route = AppScreens.AdminPrincipalActivity.route) {
            PrincipalAdministrador(navController)
        }
        composable(route = AppScreens.AdminEditModActivity.route+"/{modulo}",
            arguments = listOf(navArgument(name = "modulo") {
                type = NavType.StringType
            })) {
                backStackEntry ->
                backStackEntry?.arguments?.getString("modulo")?.let { json ->
                val modulo = Gson().fromJson(json, Modulo::class.java)
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
                val user = Gson().fromJson(json, Estudiante::class.java)
                EditUserComposable(navController, user)
            }
        }
        composable(route = AppScreens.AdminLeccionesActivity.route+"/{modulo}",arguments = listOf(navArgument(name = "modulo") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("modulo")?.let { json ->
                val modulo = Gson().fromJson(json, Modulo::class.java)
                LeccionesAdminComposable(navController,modulo)
            }
        }
        composable(route = AppScreens.AdminEjerciciosActivity.route+"/{modulo}",arguments = listOf(navArgument(name = "modulo") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("modulo")?.let { json ->
                val modulo = Gson().fromJson(json, Modulo::class.java)
                EjerciciosAdminComposable(navController,modulo)
            }
        }

        composable(route = AppScreens.AdminEditEjerActivity.route+"/{ejercicio}",arguments = listOf(navArgument(name = "ejercicio") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("ejercicio")?.let { json ->
                val ejercicio = Gson().fromJson(json, Ejercicio::class.java)
                AdminEditEjercicioComposable(navController,ejercicio)
            }
        }

        composable(route = AppScreens.AdminEditLeccActivity.route+"/{leccion}",arguments = listOf(navArgument(name = "leccion") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("leccion")?.let { json ->
                val leccion = Gson().fromJson(json, Leccion::class.java)
                AdminEditLeccionComposable(navController,leccion)
            }
        }

        composable(route = AppScreens.AdminModulosActivity.route) {
            ModulosAdminComposable(navController)
        }
        composable(route = AppScreens.AdminUsuariosActivity.route) {
            UsuariosComposable(navController)
        }
    }
}