package com.selamikadioglu.native_mobile_case_study.base
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.MalformedJsonException
import java.io.IOException
import java.io.Reader
import java.io.StringReader


object JsonValidationUtil {

    @Throws(IOException::class)
    fun isJsonValid(json: String?): Boolean {
        return isJsonValid(StringReader(json))
    }

    @Throws(IOException::class)
    private fun isJsonValid(reader: Reader): Boolean {
        return isJsonValid(JsonReader(reader))
    }

    @Throws(IOException::class)
    private fun isJsonValid(jsonReader: JsonReader): Boolean {
        return try {
            var token: JsonToken?
            loop@ while (jsonReader.peek()
                    .also { token = it } != JsonToken.END_DOCUMENT && token != null
            ) {
                when (token) {
                    JsonToken.BEGIN_ARRAY -> jsonReader.beginArray()
                    JsonToken.END_ARRAY -> jsonReader.endArray()
                    JsonToken.BEGIN_OBJECT -> jsonReader.beginObject()
                    JsonToken.END_OBJECT -> jsonReader.endObject()
                    JsonToken.NAME -> jsonReader.nextName()
                    JsonToken.STRING, JsonToken.NUMBER, JsonToken.BOOLEAN, JsonToken.NULL -> jsonReader.skipValue()
                    JsonToken.END_DOCUMENT -> break@loop
                    else -> throw AssertionError(token)
                }
            }
            true
        } catch (ignored: MalformedJsonException) {
            false
        }
    }
}