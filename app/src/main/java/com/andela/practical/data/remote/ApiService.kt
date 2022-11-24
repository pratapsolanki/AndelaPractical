package com.andela.practical.data.remote

import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.HistoryData
import com.andela.practical.domain.models.OtherCurrency
import com.andela.practical.domain.models.Symbol
import com.andela.practical.util.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("apikey: ${ApiConstants.KEY}")
    @GET(ApiConstants.ALL_SYMBOL)
    suspend fun getALLCurrencySymbol(): Response<Symbol>

    @Headers("apikey: ${ApiConstants.KEY}")
    @GET(ApiConstants.CONVERT)
    suspend fun getConvert(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: String
    ): Response<ConvertResult>


    @Headers("apikey: ${ApiConstants.KEY}")
    @GET(ApiConstants.TIME_SERIES)
    suspend fun getThreeDayHistory(
        @Query("source") source: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Response<HistoryData>


    @Headers("apikey: ${ApiConstants.KEY}")
    @GET(ApiConstants.OTHER_CURRENCY)
    suspend fun getCurrency(
        @Query("source") source: String,
        @Query("currencies") currencies: String
    ): Response<OtherCurrency>
}