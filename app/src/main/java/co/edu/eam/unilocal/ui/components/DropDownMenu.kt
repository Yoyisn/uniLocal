package co.edu.eam.unilocal.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DropdownMenu ( label: String, list: List<String>, onValueChange: (String) -> Unit, icon: ImageVector, supportingText: String) {

    var isError by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by  remember { mutableStateOf("") }

    ExposedDropdownMenuBox ( expanded = expanded, onExpandedChange = { expanded = !expanded} ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedItem,
            onValueChange = { selectedItem = it },
            label = { Text( text = label ) },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text ( text = supportingText )
                }
            },
            leadingIcon = { Icon(
                imageVector = icon,
                contentDescription = label
            ) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )//End OutlinedTextField

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            list.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        selectedItem = it
                        expanded = false
                        onValueChange (selectedItem)
                    }
                )
            }//End cities.forEach
        }//End ExposedDropdownMenu

    }
}