package com.tcc.money.data.applications

import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository

class LoginUseCase {

    private val usersRepository = UsersRepository()

   suspend fun execute(user: Users): Users {

        return usersRepository.login(user)
    }

}