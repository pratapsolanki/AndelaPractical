package com.andela.practical.util

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection

class ErrorHandling {


    companion object {

        fun exceptionHandling(e: Exception): String? {

            when (e) {
                is HttpException -> {
                    return if (e.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                        "Unauthorised User"
                    } else if (e.code() == HttpsURLConnection.HTTP_FORBIDDEN) {
                        "Forbidden"
                    } else if (e.code() == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                        "Internal Error"
                    } else if (e.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                        "Bad Request"
                    } else {
                        e.localizedMessage
                    }
                }
                is JsonSyntaxException -> {
                    return e.localizedMessage
                }
                is NullPointerException -> {
                    return "Null"
                }
                else -> {
                    return e.message
                }
            }
        }
    }


}