package com.herika.cryptowiki.data

import com.herika.cryptowiki.data.dto.CoinDto
import com.herika.cryptowiki.data.dto.CoinListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApi {
    @GET("coins")
    suspend fun  getCoins() : List<CoinListDto>

    @GET("coins/{coinId}")
    suspend fun getCoin(@Path("coinId") coinId : String) : CoinDto
}