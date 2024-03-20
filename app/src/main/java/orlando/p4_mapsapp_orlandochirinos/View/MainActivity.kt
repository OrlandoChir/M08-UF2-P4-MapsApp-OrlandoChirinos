package orlando.p4_mapsapp_orlandochirinos.View

import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import orlando.p4_mapsapp_orlandochirinos.ui.theme.P4MapsAppOrlandoChirinosTheme
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationController = rememberNavController()
            val mapViewmodel : MapViewmodel = MapViewmodel()
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
                        composable(Routes.MapScreen.route) { MapScreen(mapViewmodel,navigationController) }
                        composable(Routes.MarkerListScreen.route) { MarkerListScreen(mapViewmodel,navigationController) }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    P4MapsAppOrlandoChirinosTheme {
        Greeting("Android")
    }
}
*/
