package com.andela.practical.presentation.historyData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andela.practical.data.repo.RemoteRepositoryImpl
import com.andela.practical.domain.models.HistoryData
import com.andela.practical.domain.models.OtherCurrency
import com.andela.practical.util.ErrorHandling
import com.andela.practical.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDataViewModel @Inject constructor(private val remoteRepositoryImpl: RemoteRepositoryImpl) :
    ViewModel() {

    private val _historyUIStateFlow = MutableStateFlow<Resource<HistoryData>>(Resource.Loading())
    val historyUIState = _historyUIStateFlow.asStateFlow()

    private val _currencyUIStateFlow = MutableStateFlow<Resource<OtherCurrency>>(Resource.Loading())
    val currencyUUIState = _currencyUIStateFlow.asStateFlow()

    fun getHistory(baseCurrency: String, currentDay: String, lastDay: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _historyUIStateFlow.emit(Resource.Loading())
                val response = remoteRepositoryImpl.getThreeDayHistory(baseCurrency, currentDay, lastDay)
                if (response.isSuccessful) {
                    response.let {
                        _historyUIStateFlow.emit(Resource.Success(response.body()!!))
                    }
                }
            } catch (e: Exception) {
                _historyUIStateFlow.emit(Resource.Error(ErrorHandling.exceptionHandling(e)!!))
            }
        }
    }

    fun getCurrency(baseCurrency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _currencyUIStateFlow.emit(Resource.Loading())
                val response = remoteRepositoryImpl.getCurrency(baseCurrency)
                if (response.isSuccessful) {
                    response.let {
                        _currencyUIStateFlow.emit(Resource.Success(response.body()!!))
                    }
                }
            } catch (e: Exception) {
                _currencyUIStateFlow.emit(Resource.Error(ErrorHandling.exceptionHandling(e)!!))
            }
        }
    }
}
