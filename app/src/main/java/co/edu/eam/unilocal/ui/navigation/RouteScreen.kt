package co.edu.eam.unilocal.ui.navigation

import co.edu.eam.unilocal.ui.user.nav.UserScreen
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

    @Serializable
    data object AddPlacesScreen : RouteScreen()

    @Serializable
    data class PlaceDetail(val id: String) : RouteScreen()

}