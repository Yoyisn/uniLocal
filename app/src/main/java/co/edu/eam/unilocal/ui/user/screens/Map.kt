package co.edu.eam.unilocal.ui.user.screens

import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.components.MapBox
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Map () {

    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val places by placesViewModel.places.collectAsState()

   Box () {
       MapBox (
           places = places,
           modifier = Modifier.fillMaxSize(),
       )//End MapBox
   }//End Box

}//End fun Map