package com.tcc.money.data.applications

import com.tcc.money.data.api.models.Users
import com.tcc.money.data.api.repositories.UsersRepository

class LoginUseCase {

    private val usersRepository = UsersRepository()

    fun execute(user: Users): Users {

        return usersRepository.login(user)
    }

}