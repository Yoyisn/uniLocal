package co.edu.eam.unilocal

import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import co.edu.eam.unilocal.ui.screens.Navigation
import co.edu.eam.unilocal.ui.theme.uniLocalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent(
            content = {
                uniLocalTheme{
                    Navigation()
                }//End uniLocalTheme
            }//End content
        )//End set content
    }
}