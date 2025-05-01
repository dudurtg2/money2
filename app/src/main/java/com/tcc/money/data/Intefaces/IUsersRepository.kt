package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Users

interface IUsersRepository {
    fun save(users: Users): Users
}