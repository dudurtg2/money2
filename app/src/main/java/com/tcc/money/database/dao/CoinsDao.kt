package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.database.entities.CoinsEntity

@Dao
interface CoinsDao {
    @Insert
    fun save(coinsEntity: CoinsEntity): CoinsEntity

    @Query("SELECT * FROM coins WHERE id = :id")
    fun findByid(id: Long): CoinsEntity

    @Query("SELECT * FROM coins")
    fun findAll(): List<CoinsEntity>

    @Query("SELECT * FROM coins WHERE sync = 0")
    fun findByNotSync(): List<CoinsEntity>
}