package mx.ipn.escom.TTA024.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import mx.ipn.escom.TTA024.ui.AdminUI.AdminEditEjercicioComposable
import mx.ipn.escom.TTA024.ui.AdminUI.AdminEditLeccionComposable
import mx.ipn.escom.TTA024.ui.AdminUI.EditModulo
import mx.ipn.escom.TTA024.ui.AdminUI.EditUserComposable
import mx.ipn.escom.TTA024.ui.AdminUI.ModulosAdminComposable
import mx.ipn.escom.TTA024.ui.AdminUI.PrincipalAdministrador
import mx.ipn.escom.TTA024.ui.AdminUI.UsuariosComposable
import mx.ipn.escom.TTA024.EjerciciosAdminComposable
import mx.ipn.escom.TTA024.LeccionesAdminComposable
import mx.ipn.escom.TTA024.data.models.EjercicioModel
import mx.ipn.escom.TTA024.data.models.EstudianteModel
import mx.ipn.escom.TTA024.data.models.LeccionModel
import mx.ipn.escom.TTA024.data.models.ModuloModel
import mx.ipn.escom.TTA024.ui.viewmodels.ModuloViewModel

@Composable
fun AppNavigation(moduloViewModel: ModuloViewModel) {
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
                val user = Gson().fromJson(json, EstudianteModel::class.java)
                EditUserComposable(navController, user)
            }
        }
        composable(route = AppScreens.AdminLeccionesActivity.route+"/{modulo}",arguments = listOf(navArgument(name = "modulo") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("modulo")?.let { json ->
                val modulo = Gson().fromJson(json, ModuloModel::class.java)
                LeccionesAdminComposable(navController,modulo)
            }
        }
        composable(route = AppScreens.AdminEjerciciosActivity.route+"/{modulo}",arguments = listOf(navArgument(name = "modulo") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("modulo")?.let { json ->
                val modulo = Gson().fromJson(json, ModuloModel::class.java)
                EjerciciosAdminComposable(navController,modulo)
            }
        }

        composable(route = AppScreens.AdminEditEjerActivity.route+"/{ejercicio}",arguments = listOf(navArgument(name = "ejercicio") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("ejercicio")?.let { json ->
                val ejercicio = Gson().fromJson(json, EjercicioModel::class.java)
                AdminEditEjercicioComposable(navController,ejercicio)
            }
        }

        composable(route = AppScreens.AdminEditLeccActivity.route+"/{leccion}",arguments = listOf(navArgument(name = "leccion") {
            type = NavType.StringType
        })) {
                backStackEntry ->
            backStackEntry?.arguments?.getString("leccion")?.let { json ->
                val leccion = Gson().fromJson(json, LeccionModel::class.java)
                AdminEditLeccionComposable(navController,leccion)
            }
        }

        composable(route = AppScreens.AdminModulosActivity.route) {
            ModulosAdminComposable(navController,moduloViewModel)
        }
        composable(route = AppScreens.AdminUsuariosActivity.route) {
            UsuariosComposable(navController)
        }
    }
}