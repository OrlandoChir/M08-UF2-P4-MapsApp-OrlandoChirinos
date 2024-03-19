package orlando.p4_mapsapp_orlandochirinos.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import orlando.p4_mapsapp_orlandochirinos.ModelView.MapViewmodel
import orlando.p4_mapsapp_orlandochirinos.Models.Ubicacion
import orlando.trivial.orlandochirinos_apilistapp.Navigation.Routes


@Composable
fun MarkerListScreen(mapViewmodel: MapViewmodel, navigationController: NavHostController) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)){
        items(mapViewmodel.getAllLocations()){ location ->
            LocationItem(location,mapViewmodel,navigationController)
        }
    }

}

@Composable
fun LocationItem(location: Ubicacion, mapViewmodel: MapViewmodel, navigationController: NavHostController) {
    Card( border = BorderStroke(4.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
            .clickable {
              //  navigationController.navigate(Routes.MapScreen.spawnOnPosition(location.position))
                mapViewmodel.changePosition(location.position)
                navigationController.navigate(Routes.MapScreen.route)
            }
    )
    {
        Row {
            //AUN NO TENGO IMAGEN QUE MOSTRAR.
/*            GlideImage(
                model = agent.displayIcon,
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(0.25f)
            )*/
            Column {
                Text(text = location.nombre,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp) )
                Text(text = "Descripción: ${location.snippet}\n" +
                            "Etiqueta: ${location.tag}")
            }
        }
    }
}
