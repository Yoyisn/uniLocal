package co.edu.eam.unilocal.ui.user.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.edu.eam.unilocal.ui.user.screens.EditUserScreen
import co.edu.eam.unilocal.ui.user.screens.Map
import co.edu.eam.unilocal.ui.places.Places
import co.edu.eam.unilocal.ui.user.screens.Profile
import co.edu.eam.unilocal.ui.user.screens.Search

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentUser (
    padding: PaddingValues,
    navController: NavHostController,
    onNavigateToPlaceDetail: (String) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = UserScreen.Map
    ){
        composable<UserScreen.Map> {
            Map()
        }
        composable<UserScreen.Search> {
            Search(
                padding = padding,
                onNavigateToPlaceDetail = onNavigateToPlaceDetail
            )
        }
        composable<UserScreen.Places> {
            Places(
                padding = padding,
                //placesViewModel = placesViewModel,
                onNavigateToPlaceDetail = onNavigateToPlaceDetail
            )
        }
        composable<UserScreen.Profile> {
            Profile( onNavigateToEditProfile = { navController.navigate(UserScreen.EditProfileScreen) } )
        }
        composable<UserScreen.EditProfileScreen> {
            EditUserScreen( onNavigateToEditProfile = { navController.navigate(UserScreen.Profile) } )
        }

    }
}//End fun ContentUser