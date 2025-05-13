package com.tcc.money.database.relacion

import androidx.room.Embedded
import androidx.room.Relation
import com.tcc.money.database.entities.CoinsEntity
import com.tcc.money.database.entities.MovementsEntity

data class MovementWithCoins(
    @Embedded val movement: MovementsEntity,
    @Relation(
        parentColumn = "coinsId",
        entityColumn = "id"
    )
    val coins: CoinsEntity
)