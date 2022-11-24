package com.andela.practical.data.repo

import com.andela.practical.data.remote.ApiService
import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.HistoryData
import com.andela.practical.domain.models.OtherCurrency
import com.andela.practical.domain.models.Symbol
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) {
    /**
     * get all currency symbol
     */
    suspend fun getALLCurrencySymbol(): Response<Symbol> = apiService.getALLCurrencySymbol()

    /**
     * function for convert base currency to source currency
     */
    suspend fun getConvert(to: String, from: String, amount: String): Response<ConvertResult> =
        apiService.getConvert(to, from, amount)

    /**
     * function for get last 3 day currency history
     */
    suspend fun getThreeDayHistory(
        baseCurrency: String,
        currentDay: String,
        lastDay: String
    ): Response<HistoryData> = apiService.getThreeDayHistory(baseCurrency, currentDay, lastDay)

    /***
     * get rates from base currency with others from top 10 currency
     */
    suspend fun getCurrency(baseCurrency: String): Response<OtherCurrency> =
        apiService.getCurrency(baseCurrency, "AED,CAD,CNY,EUR,GBP,INR,IRR,RUB,USD,ZAR")

}