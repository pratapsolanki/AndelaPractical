package com.andela.practical.presentation.convertCurrency

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.andela.practical.data.repo.RemoteRepositoryImpl
import com.andela.practical.domain.models.Symbol
import com.andela.practical.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConvertCurrencyViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    private val remoteRepositoryImpl: RemoteRepositoryImpl = mock()

    private lateinit var convertCurrencyViewModel: ConvertCurrencyViewModel

    @Before
    fun setUp() {
        convertCurrencyViewModel = ConvertCurrencyViewModel(remoteRepositoryImpl)
    }

    @Test
    fun `should get all currency flow`() = runTest {

    }
}
