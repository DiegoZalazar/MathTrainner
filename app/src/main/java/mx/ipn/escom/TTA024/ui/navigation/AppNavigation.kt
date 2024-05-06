package mx.ipn.escom.TTA024.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
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
import mx.ipn.escom.TTA024.domain.model.Ejercicio
import mx.ipn.escom.TTA024.domain.model.Leccion
import mx.ipn.escom.TTA024.domain.model.Modulo
import mx.ipn.escom.TTA024.ui.AdminUI.AdminFormEjercicioComposable
import mx.ipn.escom.TTA024.ui.AdminUI.AdminFormLeccionComposable
import mx.ipn.escom.TTA024.ui.viewmodels.AdminEjerciciosViewModel
import mx.ipn.escom.TTA024.ui.viewmodels.AdminLeccionesViewModel
import mx.ipn.escom.TTA024.ui.viewmodels.ModulosAdminViewModel
@Composable
fun AppNavigation(modulosAdminViewModel: ModulosAdminViewModel,
                  adminLeccionesViewModel: AdminLeccionesViewModel,
                  adminEjerciciosViewModel: AdminEjerciciosViewModel) {
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
            UsuariosComposable(navController)
        }
    }
}