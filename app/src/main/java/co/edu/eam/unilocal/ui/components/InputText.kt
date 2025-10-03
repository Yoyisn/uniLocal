package co.edu.eam.unilocal.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun InputText (
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    multiline: Boolean = false,
    icon: ImageVector? = null,
    supportingText: String,
    onValidate: (String) -> Boolean,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {

    var  isError by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text( text = label ) },
        value = value,
        enabled = enabled,
        shape = RoundedCornerShape(15.dp),
        singleLine = !multiline,
        minLines = if (multiline) 3 else 1,
        isError = isError,
        supportingText = {
            if (isError) {
                Text ( text = supportingText )
            }
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            if ( icon != null ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label
                )
            }
        },
        onValueChange = {
            onValueChange(it)
            isError = onValidate(it)
        }
    ) //End OutlinedTextField

}//End InputTextError