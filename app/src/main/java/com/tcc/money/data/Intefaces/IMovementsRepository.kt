package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.Movements

interface IMovementsRepository {
    fun save(movements: Movements): Movements
}