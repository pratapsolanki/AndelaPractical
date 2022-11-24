package com.andela.practical.presentation.convert_currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andela.practical.data.repo.RemoteRepositoryImpl
import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.Symbol
import com.andela.practical.util.ErrorHandling
import com.andela.practical.util.Logger
import com.andela.practical.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *  By @Inject annotation you can access specific class from container  in Hilt
 */
@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(private val remoteRepositoryImpl: RemoteRepositoryImpl) :
    ViewModel() {

    private val convertUIState = MutableLiveData<Resource<ConvertResult>>(Resource.Loading())

    private var currencyLiveData = MutableLiveData<Resource<Symbol>>()


    fun getAllCurrency() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    Logger.d("Called")
                    currencyLiveData.postValue(
                        Resource.Success(remoteRepositoryImpl.getALLCurrencySymbol().body()!!)
                    )
                }
            } catch (e: Exception) {
                currencyLiveData.postValue(Resource.Error(ErrorHandling.exceptionHandling(e)!!))
            }
        }
    }


    fun observeCurrencyLiveData(): LiveData<Resource<Symbol>> {
        return currencyLiveData
    }

    fun observerFullLiveData(): LiveData<Resource<ConvertResult>> {
        return convertUIState
    }


    fun fetchValue(to: String, from: String, amount: String) = viewModelScope.launch {
        val response = remoteRepositoryImpl.getConvert(to, from, amount)
        convertUIState.postValue(Resource.Loading())
        if (response.isSuccessful) {
            convertUIState.postValue(Resource.Success(response.body()!!))
        } else {
            convertUIState.postValue(Resource.Error(response.message()))
        }
    }

}