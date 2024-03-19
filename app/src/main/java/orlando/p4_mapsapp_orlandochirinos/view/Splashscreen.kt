package orlando.p4_mapsapp_orlandochirinos.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes
import orlando.trivial.p4_mapsapp_orlandochirinos.R

@Composable
fun Splash(alphaAnim: Float) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "ITB MAPITA",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold)

        Image(
            modifier = Modifier.padding(15.dp).clip(CircleShape),
            painter = painterResource(id = R.drawable.mapa),
            contentDescription = "logo", alpha = alphaAnim
        )

    }
}
@Composable
fun SplashScreen(navController: NavController) {
    var startAnimation by rememberSaveable { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue =   if (startAnimation) { 1f }
                        else { 0f },
        animationSpec = tween(durationMillis = 3000), label = ""
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(5000)
        navController.popBackStack()
        navController.navigate(Routes.LoginScreen.route)
    }
    Splash(alphaAnim.value)
}