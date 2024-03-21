package mx.ipn.escom.TTA024.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import mx.ipn.escom.TTA024.Admin.EditModulo
import mx.ipn.escom.TTA024.Admin.EditUserComposable
import mx.ipn.escom.TTA024.Admin.ModulosAdminComposable
import mx.ipn.escom.TTA024.Admin.PrincipalAdministrador
import mx.ipn.escom.TTA024.Admin.UsuariosComposable
import mx.ipn.escom.TTA024.AdminLeccionesComposable
import mx.ipn.escom.TTA024.EjerciciosAdmin
import mx.ipn.escom.TTA024.models.Estudiante

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
        composable(route = AppScreens.AdminEditModActivity.route) {
            EditModulo(navController)
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
        composable(route = AppScreens.AdminEjerciciosActivity.route) {
            EjerciciosAdmin(navController)
        }
        composable(route = AppScreens.AdminLeccionesActivity.route) {
            AdminLeccionesComposable(navController)
        }
        composable(route = AppScreens.AdminModulosActivity.route) {
            ModulosAdminComposable(navController)
        }
        composable(route = AppScreens.AdminUsuariosActivity.route) {
            UsuariosComposable(navController)
        }
    }
}