package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Movements

interface IMovementsRepository {
    suspend fun save(movements: Movements): Movements
    suspend fun findById(id: Long): Movements
    suspend fun findAll(): List<Movements>
}