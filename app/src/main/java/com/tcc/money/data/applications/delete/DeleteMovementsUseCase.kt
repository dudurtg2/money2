package com.tcc.money.data.applications.delete

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class DeleteMovementsUseCase(
    private val repository: MovementsRepository,
    private val dao: MovementsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: MovementsMapper
) {
    suspend fun execute(uuid: UUID) = withContext(Dispatchers.IO) {
        val isPremium = checkPremium.execute()

        if (isPremium) {
            repository.delete(uuid)
            dao.delete(uuid)
        } else {
            dao.delete(uuid)
        }
    }
}