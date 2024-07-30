package com.selamikadioglu.native_mobile_case_study.base

import com.google.gson.annotations.SerializedName


data class ErrorData(

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("messages")
    val messages: Any?
)