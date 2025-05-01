package com.tcc.money.data.applications

import com.tcc.money.data.api.models.Movements
import com.tcc.money.data.api.repositories.MovementsRepository
import com.tcc.money.data.local.repositories.MovementsRepositoryLocal

class findMovementsUseCase {
    private val movementsRepository = MovementsRepository()
    private val movementsRepositoryLocal = MovementsRepositoryLocal()
    private val hasPremiumAccountUseCase = hasPremiumAccountUseCase().execute()

    fun execute(): List<Movements> {
        if (hasPremiumAccountUseCase) {
            return movementsRepository.findAll()
        } else {
            return movementsRepositoryLocal.findAll()
        }
    }

    fun execute(id: Int): Movements {
        if (hasPremiumAccountUseCase) {
            return movementsRepository.findById(id)
        } else {
            return movementsRepositoryLocal.findById(id)
        }
    }
}