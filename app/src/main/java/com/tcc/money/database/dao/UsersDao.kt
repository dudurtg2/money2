package com.tcc.money.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Users
import com.tcc.money.database.entities.UsersEntity
import java.util.UUID

@Dao
interface UsersDao {
    @Insert
    fun save(users: UsersEntity): Long

    @Query("SELECT * FROM users WHERE uuid = :uuid")
    fun findByUUID(uuid: UUID): UsersEntity

    @Query("SELECT * FROM users WHERE sync = 0")
    fun findByNotSync(): List<UsersEntity>

    @Query("SELECT * FROM users WHERE email = :email")
    fun findByEmail(email: String): UsersEntity
}
