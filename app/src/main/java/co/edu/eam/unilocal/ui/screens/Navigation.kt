package co.edu.eam.unilocal.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.eam.unilocal.ui.config.RouteScreen
import co.edu.eam.unilocal.ui.screens.admin.HomeAdminScreen
import co.edu.eam.unilocal.ui.screens.user.HomeUserScreen

@Composable
fun Navigation () {

    val navController = rememberNavController()

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
                onNavigateToHome = {
                    navController.navigate(RouteScreen.HomeUser)
                }
            )
        }

        composable<RouteScreen.SigIn> {
            SigInFormScreen(
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