package co.edu.eam.unilocal.ui.screens.user.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import co.edu.eam.unilocal.ui.places.AddPlacesScreen
import co.edu.eam.unilocal.ui.screens.user.EditUserScreen
import co.edu.eam.unilocal.ui.screens.user.tabs.Map
import co.edu.eam.unilocal.ui.places.PlaceDetail
import co.edu.eam.unilocal.ui.screens.user.tabs.Places
import co.edu.eam.unilocal.ui.screens.user.tabs.Profile
import co.edu.eam.unilocal.ui.screens.user.tabs.Search
import co.edu.eam.unilocal.viewModel.PlacesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentUser (
    padding: PaddingValues,
    navController: NavHostController
) {

    val placesViewModel: PlacesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = RouteTab.Map
    ){
        composable<RouteTab.Map> {
            Map()
        }
        composable<RouteTab.Search> {
            Search( padding = padding )
        }
        composable<RouteTab.Places> {
            Places(
                padding = padding,
                placesViewModel = placesViewModel,
                onNavigateToPlaceDetail = { navController.navigate(RouteTab.PlaceDetail(it))}
            )
        }
        composable<RouteTab.Profile> {
            Profile( onNavigateToEditProfile = { navController.navigate(RouteTab.EditProfileScreen) } )
        }
        composable<RouteTab.EditProfileScreen> {
            EditUserScreen( onNavigateToEditProfile = { navController.navigate(RouteTab.Profile) } )
        }

        composable<RouteTab.AddPlacesScreen> {
            AddPlacesScreen( onNavigateToMyPlaces = { navController.navigate(RouteTab.Places) } )
        }

        composable<RouteTab.PlaceDetail> {
            val args = it.toRoute<RouteTab.PlaceDetail>()
            PlaceDetail(
                placesViewModel = placesViewModel,
                padding = padding,
                id = args.id,
                onNavigateToMyPlaces = { navController.navigate(RouteTab.Places) }
            )
        }
    }
}//End fun ContentUser