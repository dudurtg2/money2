package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Movements
import com.tcc.money.database.entities.GoalsEntity
import com.tcc.money.database.entities.MovementsEntity
import java.util.UUID

@Dao
interface GoalsDao {
    @Insert
    suspend fun save(goalsEntity: GoalsEntity): Long

    @Query("SELECT * FROM goals WHERE uuid = :uuid")
    suspend fun findByUUID(uuid: UUID): GoalsEntity

    @Query("SELECT * FROM goals")
    suspend  fun findAll(): List<GoalsEntity>

    @Query("SELECT * FROM goals WHERE sync = 0")
    suspend fun findByNotSync(): List<GoalsEntity>

    @Update
    suspend  fun update(goalsEntity: GoalsEntity): Int

}