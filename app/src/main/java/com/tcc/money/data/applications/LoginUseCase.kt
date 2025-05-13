package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.data.services.AuthenticateService

class LoginUseCase(context: Context) {

    private val usersRepository = UsersRepository(context)


   suspend fun execute(user: Users): Users {
        val users = usersRepository.login(user)

        return usersRepository.login(user)
    }

}