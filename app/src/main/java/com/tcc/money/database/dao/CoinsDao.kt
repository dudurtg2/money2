package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.database.entities.CoinsEntity
import java.util.UUID

@Dao
interface CoinsDao {
    @Insert
    fun save(coinsEntity: CoinsEntity): Long

    @Query("SELECT * FROM coins WHERE uuid = :uuid")
    fun findByUUID(uuid: UUID): CoinsEntity

    @Query("SELECT * FROM coins")
    fun findAll(): List<CoinsEntity>

    @Query("SELECT * FROM coins WHERE sync = 0")
    fun findByNotSync(): List<CoinsEntity>

    @Update
    fun update(coinsEntity: CoinsEntity): Int
}