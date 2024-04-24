package orlando.p4_mapsapp_orlandochirinos.View

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.tryAddUser
import orlando.p4_mapsapp_orlandochirinos.R
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes

@Composable
fun RegisterScreen(mapViewmodel: MapViewmodel, navigationController: NavHostController) {

    val goToNext by mapViewmodel.goToNext.observeAsState(false)
    val error by mapViewmodel.errorMessage.observeAsState("")
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {

        Image(painter = painterResource(R.drawable.mapa), contentDescription = "Logo")

        //User email
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = mapViewmodel.loginMail,
            onValueChange = { mapViewmodel.setMail(it) },
            label = { Text("Email") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Magenta,
                unfocusedBorderColor = Color.Black )
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(mapViewmodel = mapViewmodel,1)

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(mapViewmodel = mapViewmodel,2)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color.Blue),
            onClick = {
                mapViewmodel.clearMailPasswd()
                navigationController.navigate(Routes.LoginScreen.route) } )
        { Text(text = "Back to login screen", fontSize = 15.sp, color = Color.White) }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.15f),
            colors = ButtonDefaults.buttonColors(Color.DarkGray),
            onClick = {
                if (mapViewmodel.loginPasswd == mapViewmodel.loginPasswdConfirm) {
                    tryAddUser(mapViewmodel.loginMail,mapViewmodel.loginPasswd,mapViewmodel)
                    mapViewmodel.clearMailPasswd() }
                else {
                    mapViewmodel.clearMailPasswd()
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show() } }
        ) {Text(text = "Register", fontSize = 15.sp, color = Color.White) }
    }

    BackHandler(enabled = true) {
        if (!goToNext) {
            Toast.makeText(context, "Error: No se ha iniciado sesión", Toast.LENGTH_SHORT).show() }
    }
    if (goToNext) {
        navigationController.navigate(Routes.MapScreen.route)
        mapViewmodel.screenSelect("map")
    }
    if (mapViewmodel.registerError){
        ErrorDialog(mapViewmodel.registerError,error) { mapViewmodel.showError()} }
}
