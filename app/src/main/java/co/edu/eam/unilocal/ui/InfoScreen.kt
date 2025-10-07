package co.edu.eam.unilocal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.edu.eam.uniLocal_project.R
import co.edu.eam.unilocal.ui.components.featureItem

@Composable
fun InfoScreen ( onNavigateToLogIn: () -> Unit, onNavigateToSigIn: () -> Unit ) {

    Surface {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
            modifier = Modifier
                .fillMaxSize()
                .padding(42.dp)
        ) {

            Image(
                modifier = Modifier.width(150.dp),
                painter = painterResource(R.drawable.unilocallogo),
                contentDescription = stringResource(R.string.image_txtInfoScreen)
            )//End image

            Text (
                text = stringResource(R.string.txt_infoAboutUs),
                textAlign = TextAlign.Center
            )//End text

            featureItem(
                icon = Icons.Outlined.Place,
                iconTint = Color(0xFFD5DAE6),
                text = stringResource(R.string.txt_infoLocation)
            )

            featureItem(
                icon = Icons.Outlined.Star,
                iconTint = Color(0xFFD5DAE6),
                text = stringResource(R.string.txt_infoCommentsLikes)
            )

            featureItem(
                icon = Icons.Outlined.AddCircle,
                iconTint = Color(0xFFD5DAE6),
                text = stringResource(R.string.txt_infoAddPlaces)
            )

            featureItem(
                icon = Icons.Outlined.Favorite,
                iconTint = Color(0xFFD5DAE6),
                text = stringResource(R.string.txt_infoAddFavorites)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onNavigateToLogIn() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(id = R.string.btn_logIn)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = stringResource(id = R.string.btn_logIn))
                }//End button login

                Spacer(modifier = Modifier.width(16.dp))//Space between buttons

                Button(
                    onClick = { onNavigateToSigIn() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(id = R.string.btn_sigIn)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = stringResource(id = R.string.btn_sigIn))
                }//End button sigin
            }//End row

        }//End column

    }//End surface

}