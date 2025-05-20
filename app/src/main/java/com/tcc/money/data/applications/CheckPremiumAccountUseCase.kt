package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.enums.TypeAccount

class CheckPremiumAccountUseCase(context: Context) {
    private val usersRepository = UsersRepository(context)

    fun execute(): Boolean {
        if (usersRepository.check().equals(TypeAccount.PREMIUM)) { return true } else { return false }
    }
}