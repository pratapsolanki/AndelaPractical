package com.andela.practical.util

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

object ErrorHandling {
    /**
     * function for handling error message
     */
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
            is SocketException -> {
                return "Socket Exception"
            }
            is SocketTimeoutException -> {
                return "Socket Timeout"
            }
            is UnknownHostException -> {
                return "No address associated with hostname"
            }
            else -> {
                return e.message
            }
        }
    }
}
