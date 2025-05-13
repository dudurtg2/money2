package com.tcc.money.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDateTime
@Entity(tableName = "movements")
data class MovementsEntity(
    @PrimaryKey
    val id: Long,
    var sync: Boolean,

    val date: LocalDateTime,
    val value: Float,
    val price: Float,

    @Embedded(prefix = "coins_")
    val coins: CoinsEntity,
    val typeCoins: TypeCoins
)
