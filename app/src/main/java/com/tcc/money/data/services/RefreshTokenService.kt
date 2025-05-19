package com.tcc.money.data.services

import android.content.Context
import com.tcc.money.data.repositories.UsersRepository

class RefreshTokenService(context: Context) {
    private val usersRepository = UsersRepository(context)

    private fun execute(){

        usersRepository.refreshToken()

    }
}