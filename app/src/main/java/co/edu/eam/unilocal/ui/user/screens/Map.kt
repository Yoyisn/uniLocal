package co.edu.eam.unilocal.ui.user.screens

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions

@Composable
fun Map () {

    val context = LocalContext.current

    val marker = rememberIconImage(
        key = R.drawable.red_marker,
        painter = painterResource(R.drawable.red_marker)
    )

    val mapViewportState = rememberMapViewportState{
        setCameraOptions {
            zoom ( 7.0 )
            center ( Point.fromLngLat( -75.6491181, 4.4687891) )
            pitch ( 55.0 )
        }
    }


    val hasPermission = rememberLocationPermissionState{
        Toast.makeText(
            context,
            if (it) "Permission Granted" else "permission denied",
            Toast.LENGTH_SHORT
        ).show()
    }

   Box (

   ) {
       MapboxMap (
           Modifier.fillMaxSize(),
           mapViewportState = mapViewportState
       ){
           if( hasPermission ) {
               MapEffect(key1 = "follow_puck") { mapView ->
                   mapView.location.updateSettings {
                       locationPuck = createDefault2DPuck(withBearing = true)
                       enabled = true
                       puckBearing = PuckBearing.COURSE
                       puckBearingEnabled = true
                   }
               }

               mapViewportState.transitionToFollowPuckState(
                   defaultTransitionOptions = DefaultViewportTransitionOptions.Builder().maxDurationMs(0).build()
               )
           }


           PointAnnotation(
               point = Point.fromLngLat(-75.6491181, 4.4687891)
           ){
               iconImage = marker
           }
       }//End MapboxMap
   }//End Box

}//End fun Map

@Composable
fun rememberLocationPermissionState(
    permission: String = android.Manifest.permission.ACCESS_FINE_LOCATION,
    onPermissionResult: (Boolean) -> Unit
): Boolean {
    val context = LocalContext.current
    val permissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted.value = granted
        onPermissionResult(granted)
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted.value) {
            launcher.launch(permission)
        }
    }

    return permissionGranted.value
}