package co.edu.eam.unilocal.ui.places

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import co.edu.eam.unilocal.ui.components.PlaceCard
import co.edu.eam.unilocal.ui.components.PlacesList
import co.edu.eam.unilocal.ui.navigation.LocalMainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Places(
    userId: String,
    onNavigateToPlaceDetail: (String) -> Unit,
    padding: PaddingValues,
    //placesViewModel: PlacesViewModel
) {
    //val places by placesViewModel.places.collectAsState()
    val placesViewModel = LocalMainViewModel.current.placesViewModel

    placesViewModel.getMyPlaces( userId )
    val myPlaces by placesViewModel.myPlaces.collectAsState()

    PlacesList(
        onNavigateToPlaceDetail = onNavigateToPlaceDetail,
        padding = padding,
        places = myPlaces
    )

}