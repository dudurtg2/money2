package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Movements
import java.util.UUID

interface IMovementsRepository {
    suspend fun save(movements: Movements): Movements
    suspend fun findByUUID(uuid: UUID): Movements
    suspend fun findAll(): List<Movements>
}