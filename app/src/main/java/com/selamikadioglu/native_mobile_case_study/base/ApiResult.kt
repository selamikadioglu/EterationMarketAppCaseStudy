package com.selamikadioglu.native_mobile_case_study.base

sealed class ApiResult<out R> {
    object Loading : ApiResult<Nothing>()
    object Empty : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val error: BaseError) : ApiResult<Nothing>()

    fun isSuccessOrEmpty() = this is Success || this is Empty
}

inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.Success(transform(data))
        is ApiResult.Error -> ApiResult.Error(error)
        is ApiResult.Loading -> ApiResult.Loading
        is ApiResult.Empty -> ApiResult.Empty
    }
}