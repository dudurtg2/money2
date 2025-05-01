package com.tcc.money.data.api.repositories

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.api.models.Coins

class CoinsRepository: ICoinsRepository {

    override fun save(coins: Coins): Coins {
        return coins
    }

    override fun findByid(id: Int): Coins {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Coins> {
        TODO("Not yet implemented")
    }
}