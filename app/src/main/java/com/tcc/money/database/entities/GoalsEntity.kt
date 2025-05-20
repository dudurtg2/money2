package com.tcc.money.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID
@Entity(tableName = "goals")
data class GoalsEntity(
   @PrimaryKey
   var uuid: UUID,
   var goal: Float,
   var description: String,

   @Embedded(prefix = "coins_")
   var coins: CoinsEntity,

   var data: LocalDate,
   var sync: Boolean

)
