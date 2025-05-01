package com.tcc.money.data.repository

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins

class CoinsRepository: ICoinsRepository {

    override fun save(coins: Coins): Coins {
        return coins
    }
}