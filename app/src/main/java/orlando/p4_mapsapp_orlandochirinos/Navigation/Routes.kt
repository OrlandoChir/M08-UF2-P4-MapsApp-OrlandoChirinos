package orlando.trivial.orlandochirinos_apilistapp.Navigation

import com.google.android.gms.maps.model.LatLng

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splash_screen")
    object LoginScreen: Routes("login_screen")
    object MapScreen: Routes("map_screen")
   // fun spawnOnPosition(position:LatLng) = "map_screen/$position"
    object MarkerListScreen: Routes("markerlist_screen")
    //fun createRoute(uuid:String) = "details_screen/$uuid"

}