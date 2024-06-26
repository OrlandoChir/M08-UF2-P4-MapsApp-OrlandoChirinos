package orlando.p4_mapsapp_orlandochirinos.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import orlando.p4_mapsapp_orlandochirinos.ModelView.CameraViewmodel
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.ui.theme.P4MapsAppOrlandoChirinosTheme
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationController = rememberNavController()
            val mapViewmodel : MapViewmodel = MapViewmodel()
            val cameraViewmodel : CameraViewmodel = CameraViewmodel()

            P4MapsAppOrlandoChirinosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.SplashScreen.route ) {
                        composable(Routes.SplashScreen.route) { SplashScreen(navigationController) }
                        composable(Routes.LoginScreen.route) { LoginScreen(mapViewmodel,navigationController) }
                        composable(Routes.MapScreen.route) { MapScreen(mapViewmodel,navigationController,cameraViewmodel) }
                        composable(Routes.CameraScreen.route) { CameraScreen(mapViewmodel,navigationController,cameraViewmodel) }
                        composable(Routes.TakePhotoScreen.route) { TakePhotoScreen(mapViewmodel,navigationController,cameraViewmodel) }
                        composable(Routes.GalleryScreen.route) { GalleryScreen(mapViewmodel, navigationController, cameraViewmodel,) }
                        composable(Routes.PermissionDeclinedScreen.route) { PermissionDeclinedScreen() }
                        composable(Routes.MarkerListScreen.route) { MarkerListScreen(mapViewmodel,navigationController) }
                        composable(Routes.RegisterScreen.route) { RegisterScreen(mapViewmodel,navigationController) }
                    }
                }
            }
        }
    }

}
