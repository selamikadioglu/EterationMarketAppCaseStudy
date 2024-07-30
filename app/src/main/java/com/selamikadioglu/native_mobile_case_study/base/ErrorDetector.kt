package com.selamikadioglu.native_mobile_case_study.base

interface ErrorDetector {
    fun detect(throwable: Throwable): BaseError
}