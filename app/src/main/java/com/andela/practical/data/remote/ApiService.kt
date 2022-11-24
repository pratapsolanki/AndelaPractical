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
    @Headers("apikey: G41tAZ8pnPfB2q5id9H8HqTufMAwCMxZ")
    @GET(ApiConstants.ALL_SYMBOL)
    suspend fun getALLCurrencySymbol(): Response<Symbol>

    @Headers("apikey: G41tAZ8pnPfB2q5id9H8HqTufMAwCMxZ")
    @GET(ApiConstants.CONVERT)
    suspend fun getConvert(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: String
    ): Response<ConvertResult>


    @Headers("apikey: G41tAZ8pnPfB2q5id9H8HqTufMAwCMxZ")
    @GET(ApiConstants.TIMESERIES)
    suspend fun getThreeDayHistory(
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Response<HistoryData>


    @Headers("apikey: G41tAZ8pnPfB2q5id9H8HqTufMAwCMxZ")
    @GET("https://api.apilayer.com/currency_data/live?source=USD&currencies=EUR%2CAED%2CAFN")
    suspend fun getCurrency(): Response<OtherCurrency>
}