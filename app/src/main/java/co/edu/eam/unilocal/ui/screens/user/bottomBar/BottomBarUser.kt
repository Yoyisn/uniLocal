package co.edu.eam.unilocal.ui.screens.user.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.screens.user.nav.RouteTab

@Composable
fun BottomBarUser ( navController: NavHostController ) {

    val navStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackEntry?.destination

    NavigationBar{
        Destination.entries.forEachIndexed { index, destination ->

            val isSelected = currentDestination?.route == destination.route::class.qualifiedName

            NavigationBarItem(
                label = { Text (text = stringResource(destination.label)) },
                onClick = {
                    navController.navigate(destination.route)
                },
                icon = {
                    Icon (
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.label)
                    )
                },
                selected = isSelected
            )//End NavigationBarItem
        }//End forEach
    }//End NavigationBar
}//End fun BottomBarUser

enum class Destination (
    val route: RouteTab,
    val label : Int,
    val icon: ImageVector,
){
    HOME( RouteTab.Map, R.string.txt_menuHome, Icons.Rounded.Home ),
    SEARCH( RouteTab.Search, R.string.txt_menuHomeSearch, Icons.Rounded.Search ),
    MY_PLACES( RouteTab.Places, R.string.txt_menuHomeMyPlaces, Icons.Rounded.Place ),
    PROFILE( RouteTab.Profile, R.string.txt_menuHomeProfile, Icons.Rounded.AccountBox )
}