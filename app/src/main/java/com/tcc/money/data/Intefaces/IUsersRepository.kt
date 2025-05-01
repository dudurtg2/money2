package com.tcc.money.data.Intefaces

import com.tcc.money.data.api.models.Users

interface IUsersRepository {
    fun save(users: Users): Users
    fun login(users: Users): Users
}