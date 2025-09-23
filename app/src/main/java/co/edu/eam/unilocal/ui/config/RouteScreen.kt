package co.edu.eam.unilocal.ui.config

import kotlinx.serialization.Serializable

sealed class RouteScreen {

    @Serializable
    data object Info : RouteScreen()

    @Serializable
    data object LogIn : RouteScreen()

    @Serializable
    data object SigIn : RouteScreen()

    @Serializable
    data object HomeUser : RouteScreen()

    @Serializable
    data object HomeAdmin : RouteScreen()

}