package co.edu.eam.unilocal.ui.user

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.navigation.RouteScreen
import co.edu.eam.unilocal.ui.user.bottomBar.BottomBarUser
import co.edu.eam.unilocal.ui.user.nav.ContentUser
import co.edu.eam.unilocal.ui.user.nav.UserScreen
import kotlin.math.log

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeUserScreen (
    onNavigateToAddPlaces: () -> Unit,
    onNavigateToPlaceDetail: (String) -> Unit,
    logout: () -> Unit
){
    val navController = rememberNavController()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination

    var showFAB by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(true) }
    var title by remember { mutableIntStateOf(R.string.txt_menuHome) }

    Surface {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if(showTopBar) {
                    TopBarUser(
                        title = stringResource(id = title),
                        logout = logout
                    )
                }
            },
            bottomBar = {
                BottomBarUser(
                    navController = navController,
                    showTopBar = { showTopBar = it },
                    titleTopBar = { title = it },
                    showFAB = { showFAB = it }
                )
            },
            floatingActionButton = {
                if(showFAB) {
                    FloatingActionButton(
                        onClick = {
                            onNavigateToAddPlaces()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }//End if
            }
        ) {
            padding -> ContentUser(
            navController = navController,
            padding = padding,
            onNavigateToPlaceDetail = onNavigateToPlaceDetail
            )
        }//End Scaffold
    }//End surface
}//End HomeUserScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser ( logout: () -> Unit, title: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton (
                onClick = { logout() }
            ){
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = null
                )
            }
        }
    )
}