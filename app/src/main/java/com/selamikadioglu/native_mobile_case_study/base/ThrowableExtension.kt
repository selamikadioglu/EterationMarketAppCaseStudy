package com.selamikadioglu.native_mobile_case_study.base
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException


inline fun <reified T> Throwable.toErrorData(): T? {
    if (this is HttpException) {
        try {
            val gson = Gson()
            val type = object : TypeToken<T>() {}.type
            val charStream = response()?.errorBody()?.charStream()

            charStream?.let {
                return gson.fromJson(charStream, type)
            } ?: kotlin.run {
                return null
            }
        } catch (e: JsonSyntaxException) {
            return null
        }
    }
    return null
}