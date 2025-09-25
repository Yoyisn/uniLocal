package co.edu.eam.unilocal.ui.screens.user.nav

import kotlinx.serialization.Serializable

sealed class RouteTab {

    @Serializable
    data object Map : RouteTab()

    @Serializable
    data object Search : RouteTab()

    @Serializable
    data object Places : RouteTab()

    @Serializable
    data object Profile : RouteTab()
    @Serializable
    data object EditProfileScreen : RouteTab()

    @Serializable
    data object AddPlacesScreen : RouteTab()

}