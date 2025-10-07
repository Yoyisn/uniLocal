package co.edu.eam.unilocal.ui.user.nav

import kotlinx.serialization.Serializable

sealed class UserScreen {

    @Serializable
    data object Map : UserScreen()

    @Serializable
    data object Search : UserScreen()

    @Serializable
    data object Places : UserScreen()

    @Serializable
    data object Profile : UserScreen()

    @Serializable
    data object EditProfileScreen : UserScreen()

}