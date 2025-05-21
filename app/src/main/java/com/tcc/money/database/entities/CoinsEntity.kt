package com.tcc.money.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "coins")
data class CoinsEntity(
    @PrimaryKey
    var uuid: UUID,
    var sync: Boolean,

    val name: String,
    val symbol: String,
    val image: String,


)
