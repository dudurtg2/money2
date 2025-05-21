package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Movements
import com.tcc.money.database.entities.MovementsEntity
import java.util.UUID

@Dao
interface MovementsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movementsEntity: MovementsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(movementsEntities: List<MovementsEntity>): List<Long>

    @Query("SELECT * FROM movements WHERE uuid = :uuid")
    suspend fun findByUUID(uuid: UUID): MovementsEntity

    @Query("SELECT * FROM movements")
    suspend fun findAll(): List<MovementsEntity>

    @Query("DELETE FROM movements WHERE uuid = :uuid")
    suspend fun delete(uuid: UUID): Int

    @Query("SELECT * FROM movements WHERE sync = 0")
    suspend fun findByNotSync(): List<MovementsEntity>

    @Update
    suspend fun update(movementsEntity: MovementsEntity): Int
}

