package orlando.p4_mapsapp_orlandochirinos.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import orlando.p4_mapsapp_orlandochirinos.ui.theme.P4MapsAppOrlandoChirinosTheme
import orlando.trivial.p4_mapsapp_orlandochirinos.R

@Composable
fun LoginScreen(){

    Box(modifier = Modifier.fillMaxSize()){

        Column(modifier = Modifier.align(Alignment.Center) ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .clickable { /*TODO*/ },
                painter = painterResource(id = R.drawable.loginicon),
                contentDescription = "Login")

            Spacer(modifier = Modifier.fillMaxHeight(0.05f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(CenterHorizontally),
                onClick = { /*TODO*/ }) {

                Text(text = "REGISTER")
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    P4MapsAppOrlandoChirinosTheme {
        LoginScreen()
    }
}