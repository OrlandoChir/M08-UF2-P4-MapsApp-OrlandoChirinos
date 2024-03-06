package orlando.trivial.orlandochirinos_apilistapp.Navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splash_screen")
    object MapScreen: Routes("map_screen")
    //fun createRoute(uuid:String) = "details_screen/$uuid"

}