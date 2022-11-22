package com.andela.practical.data.remote

import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.Symbol
import com.andela.practical.util.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("apikey: jdGIuaheSw3tzgUWWj92ctbnoeIutS21")
    @GET(ApiConstants.ALL_SYMBOL)
    suspend fun getALLCurrencySymbol(): Response<Symbol>

    @Headers("apikey: jdGIuaheSw3tzgUWWj92ctbnoeIutS21")
    @GET(ApiConstants.CONVERT)
    suspend fun getConvert(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: String
    ): Response<ConvertResult>

}