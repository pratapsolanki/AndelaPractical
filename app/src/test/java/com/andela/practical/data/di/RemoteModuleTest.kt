package com.andela.practical.data.di

import com.andela.practical.util.ApiConstants
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/*
@RunWith(MockitoJUnitRunner::class)
class RemoteModuleTest {

    private val remoteModule = RemoteModule()

    @Test
    fun `provide retrofit`(){
        val url = "https://api.com"
        mockkObject(ApiConstants)
        every { ApiConstants.BASE_URL } returns url
        val retrofit = remoteModule.provideRetrofit()
        assertEquals(url,retrofit.baseUrl())
    }
}*/
