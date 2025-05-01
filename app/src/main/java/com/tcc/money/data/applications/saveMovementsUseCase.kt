package com.tcc.money.data.applications

import com.tcc.money.data.api.models.Movements
import com.tcc.money.data.api.repositories.MovementsRepository
import com.tcc.money.data.local.repositories.MovementsRepositoryLocal

class saveMovementsUseCase {
    private val movementsRepository = MovementsRepository()
    private val movementsRepositoryLocal = MovementsRepositoryLocal()
    private val hasPremiumAccountUseCase = hasPremiumAccountUseCase().execute()

    fun execute(movements: Movements): Movements {
        if (hasPremiumAccountUseCase) {
            return movementsRepository.save(movements)
        } else {
            return movementsRepositoryLocal.save(movements)
        }
    }
}