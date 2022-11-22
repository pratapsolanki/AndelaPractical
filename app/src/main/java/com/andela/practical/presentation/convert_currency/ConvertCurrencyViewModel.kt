package com.pratap_solanki.andelapractical.presentation.convert_currency

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andela.practical.data.repo.RemoteRepositoryImpl
import com.andela.practical.domain.models.ConvertResult
import com.andela.practical.domain.models.Symbol
import com.andela.practical.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(private val remoteRepositoryImpl: RemoteRepositoryImpl) : ViewModel() {
    private val _currencyState = MutableStateFlow<Resource<Symbol>>(Resource.Loading())
    val currencyUIState = _currencyState.asStateFlow()

    private val _convertState = MutableStateFlow<Resource<ConvertResult>>(Resource.Loading())
    val convertUIState = _convertState.asStateFlow()

    private var movieLiveData = MutableLiveData<Resource<Symbol>>()

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    Log.d("Api","Called")
                    movieLiveData.postValue(Resource.Success(remoteRepositoryImpl.getALLCurrencySymbol().body()!!))
                }
            }catch (e: Exception){
                movieLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }


    fun observeMovieLiveData() : LiveData<Resource<Symbol>> {
        return movieLiveData
    }

    fun fetchAllCurrencySymbol() = viewModelScope.launch {
        val response = remoteRepositoryImpl.getALLCurrencySymbol()
        _currencyState.emit(Resource.Loading())
        if (response.isSuccessful) {
            _currencyState.emit(Resource.Success(response.body()!!))
        } else {
            _currencyState.emit(Resource.Error(response.message()))
        }
    }

    fun fetchValue() = viewModelScope.launch {
        val response = remoteRepositoryImpl.getConvert()
        _convertState.emit(Resource.Loading())
        if (response.isSuccessful) {
            _convertState.emit(Resource.Success(response.body()!!))
        } else {
            _convertState.emit(Resource.Error(response.message()))
        }
    }

}