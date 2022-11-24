package com.andela.practical.data.repo

import com.andela.practical.data.remote.ApiService
import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.HistoryData
import com.andela.practical.domain.models.OtherCurrency
import com.andela.practical.domain.models.Symbol
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService)  {
    suspend fun getALLCurrencySymbol(): Response<Symbol> = apiService.getALLCurrencySymbol()

    suspend fun getConvert(to :String , from :String , amount : String): Response<ConvertResult> = apiService.getConvert(to, from , amount)

    suspend fun getThreeDayHistory(currentDay : String , lastDay : String) : Response<HistoryData> = apiService.getThreeDayHistory( currentDay,lastDay )

    suspend fun getCurrency() : Response<OtherCurrency> = apiService.getCurrency( )

}