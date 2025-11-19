package co.edu.eam.unilocal.utils

sealed class RequestResult<out T> {
    object Loading : RequestResult<Nothing>()
    data class Success<T>(val data: T) : RequestResult<T>()
    data class Failure(val message: String) : RequestResult<Nothing>()
}