package com.herika.cryptowiki.domain.models

import com.herika.cryptowiki.data.dto.Tag
import com.herika.cryptowiki.data.dto.Team

data class Coin(
    val description: String,
    val id: String,
    val isActive: Boolean,
    val logo: String,
    val name: String,
    val openSource: Boolean,
    val rank: Int,
    val symbol: String,
    val tags: List<Tag>?,
    val team: List<Team>,
)
