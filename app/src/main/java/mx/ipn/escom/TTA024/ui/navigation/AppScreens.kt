package mx.ipn.escom.TTA024.ui.navigation

sealed class AppScreens(val route: String){
    object AdminEditModActivity: AppScreens("editmodulo_admin")
    object AdminEditUserActivity: AppScreens("edituser_admin")
    object AdminEjerciciosActivity: AppScreens("ejercicios_admin")
    object AdminLeccionesActivity: AppScreens("lecciones_admin")
    object AdminModulosActivity: AppScreens("modulos_admin")
    object AdminPrincipalActivity: AppScreens("principal_admin")
    object AdminUsuariosActivity: AppScreens("usuarios_admin")
    object AdminFormEjercicioActivity: AppScreens("formejercicio_admin")
    object AdminFormLeccActivity: AppScreens("formleccion_admin")
}
