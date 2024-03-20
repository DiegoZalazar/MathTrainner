package mx.ipn.escom.TTA024.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.TTA024.Admin.EditModulo
import mx.ipn.escom.TTA024.Admin.EditUserComposable
import mx.ipn.escom.TTA024.Admin.ModulosAdminComposable
import mx.ipn.escom.TTA024.Admin.PrincipalAdministrador
import mx.ipn.escom.TTA024.Admin.UsuariosComposable
import mx.ipn.escom.TTA024.AdminLeccionesComposable
import mx.ipn.escom.TTA024.EjerciciosAdmin

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.AdminPrincipalActivity.route){
        composable(route=AppScreens.AdminPrincipalActivity.route){
            PrincipalAdministrador(navController)
        }
        composable(route=AppScreens.AdminEditModActivity.route){
            EditModulo(navController)
        }
        composable(route=AppScreens.AdminEditUserActivity.route){
            EditUserComposable(navController)
        }
        composable(route=AppScreens.AdminEjerciciosActivity.route){
            EjerciciosAdmin(navController)
        }
        composable(route=AppScreens.AdminLeccionesActivity.route){
            AdminLeccionesComposable(navController)
        }
        composable(route=AppScreens.AdminModulosActivity.route){
            ModulosAdminComposable(navController)
        }
        composable(route=AppScreens.AdminUsuariosActivity.route){
            UsuariosComposable(navController)
        }
    }
}