package com.tcc.money.data.Intefaces

import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import com.tcc.money.utils.enums.TypeAccount

interface IUsersRepository {
     fun login(login: Login): Users
     fun update(users: Users): Users
     fun refreshToken()
     fun check(): TypeAccount
     fun logout()
     fun online(): Boolean
}