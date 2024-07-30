package com.selamikadioglu.native_mobile_case_study.base
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException


interface BaseRepository {
    suspend fun <T> createServiceFlow(
        failedResponseErrorDetector: ErrorDetector = ServicesFailedResponseErrorDetector(),
        call: suspend () -> Response<T>
    ): Flow<ApiResult<T>> {
        return flow {
            emit(ApiResult.Loading)
            val apiResult = apiCall(failedResponseErrorDetector, call)
            emit(apiResult)
        }.flowOn(Dispatchers.IO)
    }
    private suspend fun <T> apiCall(
        failedResponseErrorDetector: ErrorDetector,
        call: suspend () -> Response<T>
    ): ApiResult<T> {
        try {
            call.invoke().also {
                return if (it.isSuccessful) { // responseCode is in the range [200..300)
                    if (it.body() == null || it.code() == HttpResponseCode.NoContent) {
                        ApiResult.Empty
                    } else {
                        ApiResult.Success(it.body()!!)
                    }
                } else {
                    val failedResponseException = HttpException(it)
                    val error = failedResponseErrorDetector.detect(failedResponseException)
                    ApiResult.Error(error)
                }
            }
        } catch (exception: Exception) {
            if (exception is CancellationException) {
                throw exception
            }
            return ApiResult.Error(ErrorMapper.toBaseError(exception))
        }
    }
}
