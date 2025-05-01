package com.tcc.money.data.applications

import com.tcc.money.data.api.models.Coins
import com.tcc.money.data.api.repositories.CoinsRepository
import com.tcc.money.data.local.repositories.CoinsRepositoryLocal

class saveCoinsUseCase {
    private val coinsRepository = CoinsRepository()
    private val coinsRepositoryLocal = CoinsRepositoryLocal()
    private val hasPremiumAccountUseCase = hasPremiumAccountUseCase().execute()

    fun execute(coins: Coins): Coins {
        if (hasPremiumAccountUseCase) {
            return coinsRepositoryLocal.save(coins)
        } else {
            return coinsRepository.save(coins)
        }
    }


}