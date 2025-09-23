package co.edu.eam.unilocal.ui.screens.user.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.edu.eam.unilocal.ui.screens.user.tabs.Map
import co.edu.eam.unilocal.ui.screens.user.tabs.Places
import co.edu.eam.unilocal.ui.screens.user.tabs.Profile
import co.edu.eam.unilocal.ui.screens.user.tabs.Search

@Composable
fun ContentUser (
    padding: PaddingValues,
    navController: NavHostController
) {

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = RouteTab.Map
    ){
        composable<RouteTab.Map> {
            Map()
        }
        composable<RouteTab.Search> {
            Search()
        }
        composable<RouteTab.Places> {
            Places()
        }
        composable<RouteTab.Profile> {
            Profile()
        }
    }
}//End fun ContentUser