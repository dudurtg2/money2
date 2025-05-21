package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Coins
import java.util.UUID

interface ICoinsRepository {
     fun save(coins: Coins): Coins
     fun findByUUID(uuid: UUID): Coins
     fun findAll(): List<Coins>
     fun delete(uuid: UUID)
     fun update(uuid: UUID, coins: Coins): Coins
}