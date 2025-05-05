package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Users

interface IUsersRepository {
    suspend fun save(users: Users): Users
    suspend fun login(users: Users): Users
    suspend fun findById(id: Long): Users
    suspend fun update(users: Users): Users


}