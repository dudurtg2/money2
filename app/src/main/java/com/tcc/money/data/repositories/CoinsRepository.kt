package com.tcc.money.data.repositories

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.network.retrofit.CoinsRetrofit

class CoinsRepository: ICoinsRepository {

    private val api = CoinsRetrofit.instance

    override suspend fun save(coins: Coins): Coins {
        return api.save(coins)
    }

    override suspend fun findById(id: Long): Coins {
        return api.findById(id)
    }

    override suspend fun findAll(): List<Coins> {
        return api.findAll()
    }
}