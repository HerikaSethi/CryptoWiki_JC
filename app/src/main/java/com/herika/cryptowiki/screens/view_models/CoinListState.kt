package com.herika.cryptowiki.screens.view_models

import com.herika.cryptowiki.domain.models.CoinList

data class CoinListState(
    var data : List<CoinList> = emptyList(),
    var error : String = "",
    var isLoading : Boolean = true
)
