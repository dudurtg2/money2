package com.tcc.money.data.api.repositories

import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.api.models.Movements

class MovementsRepository: IMovementsRepository {
    override fun save(movements: Movements): Movements {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): Movements {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Movements> {
        TODO("Not yet implemented")
    }
}