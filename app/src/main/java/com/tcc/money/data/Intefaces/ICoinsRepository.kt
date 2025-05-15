package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Coins
import java.util.UUID

interface ICoinsRepository {
    suspend fun save(coins: Coins): Coins
    suspend fun findByUUID(uuid: UUID): Coins
    suspend fun findAll(): List<Coins>
}