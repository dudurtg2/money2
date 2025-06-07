package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Coins
import com.tcc.money.database.entities.CoinsEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CoinsMapper @Inject constructor(){

    fun toCoins(entity: CoinsEntity): Coins {
        return Coins(
            uuid = entity.uuid,
            name = entity.name,
            image = entity.image,
            symbol = entity.symbol
        )
    }

    fun toCoinsEntity(domain: Coins): CoinsEntity {
        return CoinsEntity(

            uuid = domain.uuid,
            name = domain.name,
            image = domain.image,
            symbol = domain.symbol,
            sync = false
        )
    }

    fun toCoinsList(entities: List<CoinsEntity>): List<Coins> {
        return entities.map { toCoins(it) }
    }

    fun toCoinsEntityList(domains: List<Coins>): List<CoinsEntity> {
        return domains.map { toCoinsEntity(it) }
    }
}
