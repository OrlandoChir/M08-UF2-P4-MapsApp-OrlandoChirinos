package orlando.p4_mapsapp_orlandochirinos.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.R
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@Composable
fun LoginScreen(mapViewmodel: MapViewmodel, navigationController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.Center) ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .clickable { /*TODO*/
                        mapViewmodel.screenSelect("map")
                        navigationController.navigate(Routes.MapScreen.route)
                               },

                painter = painterResource(id = R.drawable.loginicon),
                contentDescription = "Login")

            Spacer(modifier = Modifier.fillMaxHeight(0.05f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f).fillMaxHeight(0.1f)
                    .align(CenterHorizontally),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                onClick = { /*TODO*/ }) {

                Text(text = "REGISTER", fontSize = 20.sp)
            }

        }

    }


}

/*
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    P4MapsAppOrlandoChirinosTheme {
        LoginScreen(navigationController)
    }
}*/
