package co.edu.eam.unilocal.ui.user.bottomBar

import android.net.http.SslCertificate.saveState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.user.nav.UserScreen

@Composable
fun BottomBarUser (
    navController: NavHostController,
    showTopBar: (Boolean) -> Unit,
    titleTopBar: (Int) -> Unit,
    showFAB: (Boolean) -> Unit
) {

    val navStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackEntry?.destination

    LaunchedEffect(currentDestination){
        val destination = Destination.entries.find{ it.route::class.qualifiedName == currentDestination?.route }
        if(destination == null) {
            //showTopBar(destination.showTopBar)
            //showFAB(destination.showFAB)
        }
    }

    NavigationBar{
        Destination.entries.forEachIndexed { index, destination ->

            val isSelected = currentDestination?.route == destination.route::class.qualifiedName

            NavigationBarItem(
                label = { Text (text = stringResource(destination.label)) },
                onClick = {
                    navController.navigate(destination.route){
                        launchSingleTop = true
                        restoreState = true

                        showTopBar(destination.showTopBar)
                        showFAB(destination.showFAB)
                        titleTopBar(destination.label)
                    }
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
    val route: UserScreen,
    val label : Int,
    val icon: ImageVector,
    val showTopBar: Boolean = true,
    val showFAB: Boolean = false
){
    HOME( UserScreen.Map, R.string.txt_menuHome, Icons.Rounded.Home, showTopBar = true ),
    SEARCH( UserScreen.Search, R.string.txt_menuHomeSearch, Icons.Rounded.Search, showTopBar = false ),
    MY_PLACES( UserScreen.Places, R.string.txt_menuHomeMyPlaces, Icons.Rounded.Place, showTopBar = true ),
    PROFILE( UserScreen.Profile, R.string.txt_menuHomeProfile, Icons.Rounded.AccountBox, showTopBar = true )
}