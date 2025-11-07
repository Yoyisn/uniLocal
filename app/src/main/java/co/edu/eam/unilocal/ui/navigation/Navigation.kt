package co.edu.eam.unilocal.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.ui.admin.HomeAdminScreen
import co.edu.eam.unilocal.ui.user.HomeUserScreen
import co.edu.eam.unilocal.ui.InfoScreen
import co.edu.eam.unilocal.ui.auth.LoginFormScreen
import co.edu.eam.unilocal.ui.auth.SigInFormScreen
import co.edu.eam.unilocal.ui.places.AddPlacesScreen
import co.edu.eam.unilocal.ui.places.PlaceDetail
import co.edu.eam.unilocal.ui.user.nav.UserScreen
import co.edu.eam.unilocal.utils.SharedPrefsUtil
import co.edu.eam.unilocal.viewModel.MainViewModel
import co.edu.eam.unilocal.viewModel.UsersViewModel

val LocalMainViewModel = staticCompositionLocalOf<MainViewModel> { error("MainViewModel is not provided") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation (
    mainViewModel: MainViewModel,
    usersViewModel: UsersViewModel = viewModel()
) {

    val context = LocalContext.current

    val navController = rememberNavController()
    var user by remember { mutableStateOf( SharedPrefsUtil.getPreference(context) ) }

    val startDestination = if ( user.isEmpty() ) {
        RouteScreen.Info
    } else {
        if ( user["role"] == "ADMIN" ) {
            RouteScreen.HomeAdmin
        } else {
            RouteScreen.HomeUser
        }
    }

    CompositionLocalProvider( LocalMainViewModel provides mainViewModel ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ){
            composable<RouteScreen.Info> {
                InfoScreen(
                    onNavigateToLogIn = {
                        navController.navigate(RouteScreen.LogIn)
                    },
                    onNavigateToSigIn = {
                        navController.navigate(RouteScreen.SigIn)
                    }
                )
            }//End InfoScreen

            composable<RouteScreen.LogIn> {
                LoginFormScreen(
                    onNavigateToHome = { userId,  role ->

                        SharedPrefsUtil.savePreferences(context, userId, role)
                        user = SharedPrefsUtil.getPreference(context)

                        if ( role == Role.ADMIN ) {
                            navController.navigate(RouteScreen.HomeAdmin)
                        } else {
                            navController.navigate(RouteScreen.HomeUser)
                        }
                    },
                )//End loginFormScreen
            }

            composable<RouteScreen.SigIn> {
                SigInFormScreen(
                    onNavigateToHome = {
                        navController.navigate(RouteScreen.HomeUser)
                    }
                )
            }

            composable<RouteScreen.HomeUser> {
                HomeUserScreen(
                    userId = user["userId"]!!,
                    onNavigateToPlaceDetail = { navController.navigate(RouteScreen.PlaceDetail(it))},
                    onNavigateToAddPlaces = { navController.navigate(RouteScreen.AddPlacesScreen) },
                    logout = {
                        usersViewModel.logout()
                        SharedPrefsUtil.clearPreferences(context)
                        navController.navigate(RouteScreen.Info) {
                            popUpTo ( navController.graph.startDestinationId ) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<RouteScreen.HomeAdmin> {
                HomeAdminScreen()
            }

            composable<RouteScreen.AddPlacesScreen> {
                AddPlacesScreen(
                    userId = user["userId"] ?: "",
                    onNavigateBackTo = { navController.popBackStack() }
                )
            }

            composable<RouteScreen.PlaceDetail> {
                val args = it.toRoute<RouteScreen.PlaceDetail>()
                PlaceDetail(
                    placeId = args.id,
                    userId = user["userId"] ?: "",
                    onNavigateBackTo = { navController.popBackStack() },
                )
            }

        }//End NavHost
    }
}