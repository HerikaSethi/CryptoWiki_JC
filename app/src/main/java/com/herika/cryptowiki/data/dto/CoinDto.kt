package com.herika.cryptowiki.data.dto

import com.herika.cryptowiki.domain.models.Coin

data class CoinDto(
    val contract: String,
    val contracts: List<Contract>,
    val description: String,
    val development_status: String,
    val first_data_at: String,
    val hardware_wallet: Boolean,
    val hash_algorithm: String,
    val id: String,
    val is_active: Boolean,
    val is_new: Boolean,
    val last_data_at: String,
    val links: Links,
    val links_extended: List<LinksExtended>,
    val logo: String,
    val message: String,
    val name: String,
    val open_source: Boolean,
    val org_structure: String,
    val parent: Parent,
    val platform: String,
    val proof_type: String,
    val rank: Int,
    val started_at: String,
    val symbol: String,
    val tags: List<Tag>?,
    val team: List<Team>,
    val type: String,
    val whitepaper: Whitepaper
)

fun CoinDto.toCoin() : Coin {
    return Coin(
        id = id,
        description = description,
        name = name,
        logo = logo,
        isActive = is_active,
        openSource = open_source,
        rank = rank,
        symbol = symbol,
        tags = tags,
        team = team
    )
}