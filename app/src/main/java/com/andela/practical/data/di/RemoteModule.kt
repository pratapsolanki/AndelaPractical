package com.andela.practical.data.di

import android.content.Context
import com.andela.practical.util.AddLoggingInterceptor
import com.andela.practical.data.remote.ApiService
import com.andela.practical.util.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @inject -> for code you own
 * @Bind -> for injection
 * @Provider -> for code tou don't own
 */

/**
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {

    /**
     * Providing retrofit dependency for DI
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(AddLoggingInterceptor.setLogging())
            .build()
    }

    /**
     * Providing retrofit instance
     */
    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Providing HTTP dependency for DI
     */
    @Singleton
    @Provides
    fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient
            .Builder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES)
            .build()
    }

}