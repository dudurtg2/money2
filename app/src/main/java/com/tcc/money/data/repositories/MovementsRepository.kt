package com.tcc.money.data.repositories

import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.network.retrofit.CoinsRetrofit
import com.tcc.money.network.retrofit.MovementsRetrofit

class MovementsRepository: IMovementsRepository {
    private val api = MovementsRetrofit.instance

    override suspend fun save(movements: Movements): Movements {
        return api.save(movements)
    }

    override suspend fun findById(id: Long): Movements {
        return api.findById(id)
    }

    override suspend fun findAll(): List<Movements> {
        return api.findAll()
    }
}