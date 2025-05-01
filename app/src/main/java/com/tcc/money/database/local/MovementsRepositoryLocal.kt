package com.tcc.money.database.local

import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Movements

class MovementsRepositoryLocal: IMovementsRepository {
    override fun save(movements: Movements): Movements {
        TODO("Not yet implemented")
    }
}