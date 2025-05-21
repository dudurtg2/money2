package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tcc.money.database.entities.CoinsEntity
import java.util.UUID

@Dao
interface CoinsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(coinsEntity: CoinsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(coinsEntities: List<CoinsEntity>): List<Long>

    @Query("SELECT * FROM coins WHERE uuid = :uuid")
    suspend fun findByUUID(uuid: UUID): CoinsEntity

    @Query("SELECT * FROM coins")
    suspend fun findAll(): List<CoinsEntity>

    @Query("SELECT * FROM coins WHERE sync = 0")
    suspend fun findByNotSync(): List<CoinsEntity>

    @Query("DELETE FROM coins WHERE uuid = :uuid")
    suspend fun delete(uuid: UUID): Int

    @Update
    suspend fun update(coinsEntity: CoinsEntity): Int

    @Query("UPDATE coins SET sync = :sync WHERE uuid = :uuid")
    suspend fun updateSyncFlag(uuid: UUID, sync: Boolean): Int
}

