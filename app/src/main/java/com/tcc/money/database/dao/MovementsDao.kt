package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Movements
import com.tcc.money.database.entities.MovementsEntity

@Dao
interface MovementsDao {
    @Insert
    fun save(movementsEntity: MovementsEntity): Long

    @Query("SELECT * FROM movements WHERE id = :id")
    fun findById(id: Long): MovementsEntity

    @Query("SELECT * FROM movements")
    fun findAll(): List<MovementsEntity>

    @Query("SELECT * FROM movements WHERE sync = 0")
    fun findByNotSync(): List<MovementsEntity>

    @Update
    fun update(movementsEntity: MovementsEntity): Int

}