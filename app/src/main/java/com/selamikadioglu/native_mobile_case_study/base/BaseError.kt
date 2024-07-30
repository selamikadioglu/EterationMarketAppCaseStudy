package com.selamikadioglu.native_mobile_case_study.base


open class BaseError(open val throwable: Throwable? = null) :
    Throwable(cause = throwable, message = throwable?.message)

data class UnknownError(override val throwable: Throwable? = null) : BaseError(throwable)

//region Failed State Errors

sealed class HttpError(throwable: Throwable) : FailStateError(throwable)
data class BadRequestError(override val throwable: Throwable) : HttpError(throwable)
data class UnauthorizedError(override val throwable: Throwable) : HttpError(throwable)
data class NotFoundError(override val throwable: Throwable) : HttpError(throwable)
data class MethodNotAllowedError(override val throwable: Throwable) : HttpError(throwable)
data class TimeOutError(override val throwable: Throwable) : HttpError(throwable)
data class ConflictError(override val throwable: Throwable) : HttpError(throwable)
data class PreconditionFailedError(override val throwable: Throwable) : HttpError(throwable)
data class BadGatewayError(override val throwable: Throwable) : HttpError(throwable)
data class ServiceNotReachableError(override val throwable: Throwable) : HttpError(throwable)
data class JsonSyntaxError(override val throwable: Throwable) : HttpError(throwable)
data class IOError(override val throwable: Throwable) : FailStateError(throwable)
sealed class FailStateError(throwable: Throwable) : BaseError(throwable)
sealed class EmptyResponseError(
    open val errorMessage: String? = null,
    open val errorMessageStringResource: Int? = null
) :
    BaseError(throwable = Throwable(message = errorMessage))
