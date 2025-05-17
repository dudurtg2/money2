package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Movements
import com.tcc.money.database.entities.MovementsEntity
import org.mapstruct.Mapper


class MovementsMapper {
    private val coinsMapper = CoinsMapper()
    fun toMovements(entity: MovementsEntity): Movements {
        return Movements(
            uuid = entity.uuid,
            date = entity.date,
            typeCoins = entity.typeCoins,
            price = entity.price,
            coins = coinsMapper.toCoins(entity.coins),
            value = entity.value
        )
    }

    fun toMovementsEntity(domain: Movements): MovementsEntity {
        return MovementsEntity(
            uuid = domain.uuid,
            date = domain.date,
            typeCoins = domain.typeCoins,
            price = domain.price,
            coins = coinsMapper.toCoinsEntity(domain.coins),
            value = domain.value,
            sync = false
        )
    }

    fun toMovementsEntityList(list: List<Movements>): List<MovementsEntity> {
        return list.map { toMovementsEntity(it) }
    }

    fun toMovementsList(list: List<MovementsEntity>): List<Movements> {
        return list.map { toMovements(it) }
    }
}
