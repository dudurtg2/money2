package com.tcc.money.data.Intefaces

import com.tcc.money.data.api.models.Movements

interface IMovementsRepository {
    fun save(movements: Movements): Movements
    fun findById(id: Int): Movements
    fun findAll(): List<Movements>
}