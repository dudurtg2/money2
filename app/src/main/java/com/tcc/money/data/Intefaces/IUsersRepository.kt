package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Users

interface IUsersRepository {
    suspend fun login(users: Users): Users
    suspend fun update(users: Users): Users
}