package com.tcc.money.data.applications

import android.content.Context
import android.util.Log
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.enums.TypeAccount

class CheckPremiumAccountUseCase(context: Context) {
    private val usersRepository = UsersRepository(context)

    fun execute(): Boolean {
        // 1) Busca o tipo de conta
        val accountType = usersRepository.check()
        Log.d("APImoney", "Account type fetched: $accountType")

        // 2) Retorna true se for PREMIUM
        val isPremium = (accountType == TypeAccount.PREMIUM)
        Log.d("APImoney", "isPremium = $isPremium")

        return isPremium
    }
}