package co.edu.eam.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.edu.eam.unilocal.viewModel.PlacesViewModel

@Composable
fun PlaceDetail (
    id: String,
    padding: PaddingValues,
    placesViewModel: PlacesViewModel
) {

    val place = placesViewModel.findById(id)

    Box ( modifier = Modifier.padding(padding) ) {
        Text ( text = place!!.title )
    }


}