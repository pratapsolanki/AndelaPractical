package com.andela.practical.presentation.histric_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andela.practical.data.repo.RemoteRepositoryImpl
import com.andela.practical.domain.models.HistoryData
import com.andela.practical.util.ErrorHandling
import com.andela.practical.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryDataViewModel @Inject constructor(private val remoteRepositoryImpl: RemoteRepositoryImpl) :
    ViewModel() {

    private var historyData = MutableLiveData<Resource<HistoryData>>()


    fun getHistory(currentDay: String, lastDay: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    historyData.postValue(
                        Resource.Success(
                            remoteRepositoryImpl.getThreeDayHistory(
                                currentDay,
                                lastDay
                            ).body()!!
                        )
                    )
                }
            } catch (e: Exception) {
                historyData.postValue(Resource.Error(ErrorHandling.exceptionHandling(e)!!))
            }
        }
    }

    fun observeHistoryLiveData(): LiveData<Resource<HistoryData>> {
        return historyData
    }


    fun getCurrency() {
        viewModelScope.launch {
            remoteRepositoryImpl.getCurrency()
        }
    }


}