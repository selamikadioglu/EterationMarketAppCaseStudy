package com.selamikadioglu.native_mobile_case_study.base

import retrofit2.HttpException
import java.io.IOException
import java.nio.charset.StandardCharsets


object ErrorMapper {

    fun toBaseError(throwable: Throwable): BaseError {
        if (throwable is BaseError) return throwable

        val clone: Throwable = Throwable(throwable.message, throwable.cause)

        if (throwable is HttpException) {

            if (clone is HttpException) {
                val cloneBody = clone.response()?.errorBody()
                if (cloneBody != null) {
                    val errorResponseInBytes = String(cloneBody.bytes(), StandardCharsets.UTF_8)
                    if (!JsonValidationUtil.isJsonValid(errorResponseInBytes)) {
                        return JsonSyntaxError(throwable)
                    }
                }
            }

            return getError(throwable.code(), throwable)

        } else if (throwable is IOException) {
            return IOError(throwable)
        }
        return UnknownError(throwable)
    }

    fun getError(code: Int, throwable: Throwable): BaseError {
        return when (code) {
            BAD_REQUEST -> BadRequestError(throwable)
            UNAUTHORIZED -> UnauthorizedError(throwable)
            NOT_FOUND -> NotFoundError(throwable)
            METHOD_NOT_ALLOWED -> MethodNotAllowedError(throwable)
            TIME_OUT -> TimeOutError(throwable)
            CONFLICT -> ConflictError(throwable)
            PRECONDITION_FAILED -> PreconditionFailedError(throwable)
            in SERVICE_NOT_REACHABLE -> ServiceNotReachableError(throwable)
            BAD_GATEWAY -> BadGatewayError(throwable)

            else -> UnknownError(throwable)
        }
    }

    private const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    private const val NOT_FOUND = 404
    private const val METHOD_NOT_ALLOWED = 405
    private const val TIME_OUT = 408
    private const val CONFLICT = 409
    private const val PRECONDITION_FAILED = 412
    private val SERVICE_NOT_REACHABLE = 500..599
    private const val BAD_GATEWAY = 502
}