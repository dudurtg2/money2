package com.tcc.money.data.applications

import android.content.Context
import android.util.Log
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.data.services.IsApiAvailableNowService
import com.tcc.money.data.services.NetworkIsConnectedService
import com.tcc.money.data.services.SynchronizationService

import com.tcc.money.utils.enums.TypeAccount


class CheckPremiumAccountUseCase(context: Context) {
    private val usersRepository = UsersRepository(context)
    private val context = context
     fun execute(): Boolean {

        if (!IsApiAvailableNowService().execute(context)){
            Log.d("APImoney", "API is not available")
            return false
        }

        val accountType = usersRepository.check()
        Log.d("APImoney", "Account type fetched: $accountType")

        val isPremium = (accountType == TypeAccount.PREMIUM)
        Log.d("APImoney", "isPremium = $isPremium")

        return isPremium
    }
}