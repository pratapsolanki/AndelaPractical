package com.andela.practical.data.di

import io.mockk.InternalPlatformDsl.toStr
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteModuleTest {

    private val remoteModule = RemoteModule()

    @Test
    fun `provide retrofit`() {
        val url = "https://api.apilayer.com/currency_data/"
        val retrofit = remoteModule.provideRetrofit()
        assertEquals(url, retrofit.baseUrl().toStr())
    }
}
