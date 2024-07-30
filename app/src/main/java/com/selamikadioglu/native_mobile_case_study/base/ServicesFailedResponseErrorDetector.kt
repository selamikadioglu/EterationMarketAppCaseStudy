package com.selamikadioglu.native_mobile_case_study.base
import retrofit2.HttpException

open class ServicesFailedResponseErrorDetector : ErrorDetector {

    override fun detect(throwable: Throwable): BaseError {
        val errorModel: ErrorData? = throwable.toErrorData()

        return if (errorModel == null) {
            ErrorMapper.toBaseError(throwable)
        } else if (throwable is HttpException && throwable.code() in 500..599) {
            ServiceNotReachableError(throwable)
        } else {
            TimeOutError(throwable)
        }
    }
}