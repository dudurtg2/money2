package com.tcc.money.data.applications

import com.tcc.money.data.api.models.Coins
import com.tcc.money.data.api.repositories.CoinsRepository
import com.tcc.money.data.local.repositories.CoinsRepositoryLocal

class findCoinsUseCase {
    private val coinsRepository = CoinsRepository()
    private val coinsRepositoryLocal = CoinsRepositoryLocal()
    private val hasPremiumAccountUseCase = hasPremiumAccountUseCase().execute()

    fun execute(): List<Coins> {
        if (hasPremiumAccountUseCase) {
            return coinsRepository.findAll()
        } else {
            return coinsRepositoryLocal.findAll()
        }
    }

    fun execute(index: Int): Coins {
        if (hasPremiumAccountUseCase) {
            return coinsRepository.findByid(index)
        } else {
            return coinsRepositoryLocal.findByid(index)
        }
    }
}