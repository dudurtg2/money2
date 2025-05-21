package com.tcc.money.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDateTime
import java.util.UUID

@Entity(
    tableName = "movements",
)
data class MovementsEntity(
    @PrimaryKey val uuid: UUID,
    var sync: Boolean,
    val date: LocalDateTime,
    val value: Float,
    val price: Float,

    @Embedded(prefix = "coins_")
    var coins: CoinsEntity,

    val typeCoins: TypeCoins
)


