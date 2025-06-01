package com.tcc.money.data.services

import android.content.Context
import android.util.Log
import com.tcc.money.data.repositories.UsersRepository

class RefreshTokenService(context: Context) {
    private val usersRepository = UsersRepository(context)

    public fun execute() {
        if (!usersRepository.online()) {
            Log.d("APImoneyR", "Refreshing token...")
            usersRepository.refreshToken()
        }
        Log.d("APImoneyR", "Token ainda valido")
    }
}