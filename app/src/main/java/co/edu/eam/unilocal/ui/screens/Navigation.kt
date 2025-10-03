package co.edu.eam.unilocal.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.eam.unilocal.ui.config.RouteScreen
import co.edu.eam.unilocal.ui.admin.HomeAdminScreen
import co.edu.eam.unilocal.ui.screens.user.HomeUserScreen
import co.edu.eam.unilocal.viewModel.UsersViewModel

@Composable
fun Navigation () {

    val navController = rememberNavController()
    val usersViewModel: UsersViewModel = viewModel ()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Info
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
                usersViewModel = usersViewModel,
                onNavigateToHome = {
                    navController.navigate(RouteScreen.HomeUser)
                }
            )
        }

        composable<RouteScreen.SigIn> {
            SigInFormScreen(
                usersViewModel = usersViewModel,
                onNavigateToHome = {
                    navController.navigate(RouteScreen.HomeUser)
                }
            )
        }

        composable<RouteScreen.HomeUser> {
            HomeUserScreen()
        }

        composable<RouteScreen.HomeAdmin> {
            HomeAdminScreen()
        }

    }//End NavHost
}