package com.tcc.money.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinsEntity(
    @PrimaryKey
    val id: Long,
    var sync: Boolean,

    val name: String,
    val symbol: String,
    val image: String,
)
