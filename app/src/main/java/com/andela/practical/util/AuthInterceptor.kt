package com.andela.practical.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("apikey", "jdGIuaheSw3tzgUWWj92ctbnoeIutS21")


        return chain.proceed(requestBuilder.build())
    }
}