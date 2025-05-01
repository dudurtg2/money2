package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Coins

interface ICoinsRepository {
    fun save(coins: Coins): Coins
}