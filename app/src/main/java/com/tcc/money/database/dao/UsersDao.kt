package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Users
import com.tcc.money.database.entities.UsersEntity

@Dao
interface UsersDao {
    @Insert
    fun save(users: UsersEntity): UsersEntity

    @Query("SELECT * FROM users WHERE id = :id")
    fun findById(id: Long): UsersEntity

    @Query("SELECT * FROM users WHERE sync = 0")
    fun findByNotSync(): List<UsersEntity>
}
