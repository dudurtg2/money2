package com.tcc.money.data.Intefaces

import com.tcc.money.data.api.models.Coins

interface ICoinsRepository {
    fun save(coins: Coins): Coins
    fun findByid(id: Int): Coins
    fun findAll(): List<Coins>
}