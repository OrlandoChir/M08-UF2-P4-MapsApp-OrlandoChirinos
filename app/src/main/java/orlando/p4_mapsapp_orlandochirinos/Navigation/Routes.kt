package orlando.trivial.orlandochirinos_apilistapp.Navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splash_screen")
    object LoginScreen: Routes("login_screen")
    object MapScreen: Routes("map_screen")
    object MarkerListScreen: Routes("markerlist_screen")
    object CameraScreen: Routes("camera_screen")
    object TakePhotoScreen: Routes("takephoto_screen")
    object PermissionDeclinedScreen: Routes("permissiondeclined_screen")

    //fun createRoute(uuid:String) = "details_screen/$uuid"

}