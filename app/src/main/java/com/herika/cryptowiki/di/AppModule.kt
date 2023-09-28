package com.herika.cryptowiki.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.herika.cryptowiki.data.CryptoApi
import com.herika.cryptowiki.data.repository.CryptoRepository
import com.herika.cryptowiki.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoApi() : CryptoApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoRepository(cryptoApi : CryptoApi) : CryptoRepository{
        return CryptoRepository(cryptoApi)

    }
}