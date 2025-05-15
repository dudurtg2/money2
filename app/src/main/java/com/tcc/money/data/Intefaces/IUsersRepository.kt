package com.tcc.money.data.Intefaces

import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users

interface IUsersRepository {
    suspend fun login(login: Login): Users
    suspend fun update(users: Users): Users
}