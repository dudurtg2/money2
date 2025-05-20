package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Goals
import com.tcc.money.database.entities.CoinsEntity
import com.tcc.money.database.entities.GoalsEntity
import org.mapstruct.Mapper

class GoalsMapper {
    private val coinsMapper = CoinsMapper()
    fun toGoals(entity: GoalsEntity): Goals {
        return Goals(
            uuid = entity.uuid,
            coins = coinsMapper.toCoins(entity.coins),
            goal = entity.goal,
            data = entity.data,
            description = entity.description
        )
    }

    fun toGoalsEntity(domain: Goals): GoalsEntity {
        return GoalsEntity(
            uuid = domain.uuid,
            coins = coinsMapper.toCoinsEntity(domain.coins),
            goal = domain.goal,
            data = domain.data,
            description = domain.description,
            sync = false
        )
    }

    fun toGoalsList(entities: List<GoalsEntity>): List<Goals> {
        return entities.map { toGoals(it) }
    }

    fun toGoalsEntityList(domains: List<Goals>): List<GoalsEntity> {
        return domains.map { toGoalsEntity(it) }
    }
}
