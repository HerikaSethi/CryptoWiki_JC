package com.herika.cryptowiki.screens.view_models

import com.herika.cryptowiki.domain.models.Coin

data class CoinDetailState(
    val coin : Coin? = null,
    val isLoading : Boolean = true,
    val error : String = ""
)
