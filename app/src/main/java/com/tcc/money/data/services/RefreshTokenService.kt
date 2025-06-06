package com.tcc.money.data.services

import android.content.Context
import android.util.Log
import com.tcc.money.data.repositories.UsersRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RefreshTokenService @Inject constructor(
    private val usersRepository: UsersRepository,
    @ApplicationContext private val context: Context
) {

    fun execute() {
        if (!usersRepository.online()) {
            Log.d("APImoneyR", "Refreshing token...")
            usersRepository.refreshToken()
        }
        Log.d("APImoneyR", "Token ainda válido")
    }
}