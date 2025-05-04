package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Coins
import com.tcc.money.database.entities.CoinsEntity
import org.mapstruct.Mapper

@Mapper
interface CoinsMapper {
    fun toCoins(coinsEntity: CoinsEntity): Coins
    fun toCoinsEntity(coins: Coins): CoinsEntity
    fun toCoinsList(coinsEntityList: List<CoinsEntity>): List<Coins>
    fun toCoinsEntityList(coinsList: List<Coins>): List<CoinsEntity>
}