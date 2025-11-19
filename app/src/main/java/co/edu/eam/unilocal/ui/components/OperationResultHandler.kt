package co.edu.eam.unilocal.ui.components

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import co.edu.eam.unilocal.utils.RequestResult
import kotlinx.coroutines.delay

@Composable
fun <T> OperationResultHandler(
    result: RequestResult<T>?,
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (String) -> Unit
) {

    when (result) {
        is RequestResult.Loading -> {
            LinearProgressIndicator()
        }

        is RequestResult.Success -> {
            AlertMessage(
                type = AlertType.SUCCESS,
                message = "Operación exitosa"
            )
        }

        is RequestResult.Failure -> {
            AlertMessage(
                type = AlertType.ERROR,
                message = result.message
            )
        }

        null -> {}
    }

    LaunchedEffect(result) {
        when (result) {
            is RequestResult.Success -> {
                delay(2000)
                onSuccess(result.data)
            }
            is RequestResult.Failure -> {
                delay(2000)
                onFailure(result.message)
            }
            else -> {}
        }
    }
}