package co.edu.eam.unilocal

import android.os.Build
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import co.edu.eam.unilocal.ui.navigation.Navigation
import co.edu.eam.unilocal.ui.theme.uniLocalTheme
import co.edu.eam.unilocal.viewModel.MainViewModel
import co.edu.eam.unilocal.viewModel.PlacesViewModel
import co.edu.eam.unilocal.viewModel.ReviewsViewModel
import co.edu.eam.unilocal.viewModel.UsersViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val usersViewModel: UsersViewModel by viewModels()
    private val reviewsViewModel: ReviewsViewModel by viewModels()
    private val placesViewModel: PlacesViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mainViewModel = MainViewModel(
            usersViewModel = usersViewModel,
            reviewsViewModel = reviewsViewModel,
            placesViewModel = placesViewModel
        )

        setContent(
            content = {
                uniLocalTheme{
                    Navigation( mainViewModel = mainViewModel )
                }//End uniLocalTheme
            }//End content
        )//End set content

    }//End onCreate
}//End class MainActivity