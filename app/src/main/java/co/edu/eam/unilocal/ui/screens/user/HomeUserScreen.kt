package co.edu.eam.unilocal.ui.screens.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.screens.user.bottomBar.BottomBarUser
import co.edu.eam.unilocal.ui.screens.user.nav.ContentUser
import co.edu.eam.unilocal.ui.screens.user.nav.RouteTab

@Composable
fun HomeUserScreen () {

    val navController = rememberNavController()

    Surface {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBarUser() },
            bottomBar = { BottomBarUser( navController = navController ) }
        ) {
            padding -> ContentUser(
            navController = navController,
            padding = padding
            )
        }//End Scaffold
    }//End surface
}//End HomeUserScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser () {
    CenterAlignedTopAppBar(
        title = { Text ( text = stringResource(R.string.txt_startTopBar) ) }
    )
}