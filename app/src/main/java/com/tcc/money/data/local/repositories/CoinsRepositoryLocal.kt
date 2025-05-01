package com.tcc.money.data.local.repositories

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.api.models.Coins

class CoinsRepositoryLocal: ICoinsRepository {
    override fun save(coins: Coins): Coins {
        TODO("Not yet implemented")
    }

    override fun findByid(id: Int): Coins {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Coins> {
        TODO("Not yet implemented")
    }
}