package com.andela.practical.data.repo

import com.andela.practical.data.remote.ApiService
import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.Symbol
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService)  {
    suspend fun getALLCurrencySymbol(): Response<Symbol> = apiService.getALLCurrencySymbol()

    suspend fun getConvert(): Response<ConvertResult> = apiService.getConvert("BZD", "USD" , "200")
}