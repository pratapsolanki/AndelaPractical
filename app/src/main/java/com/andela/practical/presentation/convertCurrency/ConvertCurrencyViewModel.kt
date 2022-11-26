package com.andela.practical.presentation.convertCurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andela.practical.data.repo.RemoteRepositoryImpl
import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.Symbol
import com.andela.practical.util.ErrorHandling
import com.andela.practical.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  By @Inject annotation you can access specific class from container  in Hilt
 */
@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(private val remoteRepositoryImpl: RemoteRepositoryImpl) :
    ViewModel() {

    private val _convertUIStateFlow = MutableStateFlow<Resource<ConvertResult>>(Resource.Loading())
    val convertUIState = _convertUIStateFlow.asStateFlow()

    private val _convertFlowState = MutableStateFlow<Resource<Symbol>>(Resource.Loading())
    val currencyFlowUI = _convertFlowState.asStateFlow()

    fun getAllCurrencyFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _convertFlowState.emit(Resource.Loading())
                val response = remoteRepositoryImpl.getALLCurrencySymbol()
                if (response.isSuccessful) {
                    response.let {
                        _convertFlowState.emit(Resource.Success(response.body()!!))
                    }
                }
            } catch (e: Exception) {
                _convertFlowState.emit(Resource.Error(ErrorHandling.exceptionHandling(e)!!))
            }
        }
    }

    fun fetchValue(to: String, from: String, amount: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _convertUIStateFlow.emit(Resource.Loading())
                val response = remoteRepositoryImpl.getConvert(to, from, amount)
                response.let {
                    if (it.isSuccessful) {
                        _convertUIStateFlow.emit(Resource.Success(response.body()!!))
                    }
                }
            } catch (e: Exception) {
                _convertUIStateFlow.emit(Resource.Error(ErrorHandling.exceptionHandling(e)!!))
            }
        }
}
