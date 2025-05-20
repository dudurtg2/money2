package com.tcc.money.database.relacion

import androidx.room.Embedded
import androidx.room.Relation
import com.tcc.money.data.models.Goals
import com.tcc.money.database.entities.CoinsEntity
import com.tcc.money.database.entities.GoalsEntity
import com.tcc.money.database.entities.MovementsEntity

data class GoalsWithCoins(
    @Embedded val goals: GoalsEntity,
    @Relation(
        parentColumn = "coinsId",
        entityColumn = "id"
    )
    val coins: CoinsEntity
)