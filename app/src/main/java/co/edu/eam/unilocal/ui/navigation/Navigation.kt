package co.edu.eam.unilocal.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import co.edu.eam.unilocal.model.Role
import co.edu.eam.unilocal.ui.InfoScreen
import co.edu.eam.unilocal.ui.admin.HomeAdminScreen
import co.edu.eam.unilocal.ui.auth.LoginFormScreen
import co.edu.eam.unilocal.ui.auth.SigInFormScreen
import co.edu.eam.unilocal.ui.places.AddPlacesScreen
import co.edu.eam.unilocal.ui.places.PlaceDetail
import co.edu.eam.unilocal.ui.user.HomeUserScreen
import co.edu.eam.unilocal.utils.SharedPrefsUtil
import co.edu.eam.unilocal.viewModel.MainViewModel

val LocalMainViewModel =
    staticCompositionLocalOf<MainViewModel> { error("MainViewModel is not provided") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val usersViewModel = mainViewModel.usersViewModel
    val currentUser by usersViewModel.currentUser.collectAsState()

    CompositionLocalProvider(LocalMainViewModel provides mainViewModel) {

        LaunchedEffect(currentUser) {
            if (currentUser == null) {
                navController.navigate(RouteScreen.Info) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                SharedPrefsUtil.savePreferences(
                    context,
                    currentUser!!.id,
                    currentUser!!.role
                )

                if (currentUser!!.role == Role.ADMIN) {
                    navController.navigate(RouteScreen.HomeAdmin) {
                        popUpTo(0) { inclusive = true }
                    }
                } else {
                    navController.navigate(RouteScreen.HomeUser) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = RouteScreen.Info
        ) {

            // ---------- INFO ----------
            composable<RouteScreen.Info> {
                InfoScreen(
                    onNavigateToLogIn = {
                        navController.navigate(RouteScreen.LogIn)
                    },
                    onNavigateToSigIn = {
                        navController.navigate(RouteScreen.SigIn)
                    }
                )
            }

            // ---------- LOGIN ----------
            composable<RouteScreen.LogIn> {
                LoginFormScreen(
                    onNavigateToHome = { _, _ ->  }
                )
            }

            // ---------- SIGN IN ----------
            composable<RouteScreen.SigIn> {
                SigInFormScreen(
                    onNavigateToHome = {  }
                )
            }

            // ---------- HOME USER ----------
            composable<RouteScreen.HomeUser> {

                val user = currentUser
                if (user == null) return@composable

                HomeUserScreen(
                    userId = user.id,
                    onNavigateToPlaceDetail = {
                        navController.navigate(RouteScreen.PlaceDetail(it))
                    },
                    onNavigateToAddPlaces = {
                        navController.navigate(RouteScreen.AddPlacesScreen)
                    },
                    logout = {
                        usersViewModel.logout()
                        SharedPrefsUtil.clearPreferences(context)
                    }
                )
            }

            // ---------- HOME ADMIN ----------
            composable<RouteScreen.HomeAdmin> {

                val user = currentUser
                if (user == null) return@composable

                HomeAdminScreen()
            }

            // ---------- ADD PLACE ----------
            composable<RouteScreen.AddPlacesScreen> {

                val user = currentUser
                if (user == null) return@composable

                AddPlacesScreen(
                    userId = user.id,
                    onNavigateBackTo = { navController.popBackStack() },
                    context = context
                )
            }

            // ---------- DETAIL ----------
            composable<RouteScreen.PlaceDetail> {

                val args = it.toRoute<RouteScreen.PlaceDetail>()
                val user = currentUser
                if (user == null) return@composable

                PlaceDetail(
                    placeId = args.id,
                    userId = user.id,
                    onNavigateBackTo = { navController.popBackStack() }
                )
            }
        }
    }
}