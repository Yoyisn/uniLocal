package co.edu.eam.unilocal.ui.places

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import co.edu.eam.unilocal.ui.components.PlacesList
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Places(
    userId: String,
    onNavigateToPlaceDetail: (String) -> Unit,
    padding: PaddingValues,
) {
    val placesViewModel = LocalMainViewModel.current.placesViewModel

    // cargar una vez cuando cambie userId
    LaunchedEffect(userId) {
        placesViewModel.getMyPlaces(userId)
    }

    val myPlaces by placesViewModel.myPlaces.collectAsState()

    PlacesList(
        onNavigateToPlaceDetail = onNavigateToPlaceDetail,
        padding = padding,
        places = myPlaces
    )
}