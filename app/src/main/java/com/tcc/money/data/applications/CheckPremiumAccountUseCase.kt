package com.tcc.money.data.applications

import android.content.Context
import android.util.Log
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.data.services.IsApiAvailableNowService
import com.tcc.money.data.services.NetworkIsConnectedService
import com.tcc.money.data.services.SynchronizationService

import com.tcc.money.utils.enums.TypeAccount
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class CheckPremiumAccountUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val isApiAvailableNowService: IsApiAvailableNowService,
    @ApplicationContext private val context: Context
) {
    suspend fun execute(): Boolean {
        if (!isApiAvailableNowService.execute(context)) {
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