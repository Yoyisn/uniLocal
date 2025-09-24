package co.edu.eam.unilocal.ui.screens.user.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.components.UserCard

@Composable
fun Profile ( onNavigateToEditProfile: () -> Unit ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        UserCard(
            name = "Jordy Martinez",
            location = "Armenia - Quindio",
            description = "Todo fresas que la vida es crema",
            imageRes = R.drawable.profile,
            onEditClick = { onNavigateToEditProfile() }
        )
    }
}