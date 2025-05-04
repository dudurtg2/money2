package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Coins

interface ICoinsRepository {
    suspend fun save(coins: Coins): Coins
    suspend fun findById(id: Long): Coins
    suspend fun findAll(): List<Coins>
}