package com.tcc.money.data.applications.save

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class SaveMovementsUseCase(
    private val repository: MovementsRepository,
    private val dao: MovementsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: MovementsMapper
) {
    suspend fun execute(input: Movements): Movements = withContext(Dispatchers.IO) {
        val movWithId = input.copy(uuid = UUID.randomUUID())
        val isPremium = checkPremium.execute()

        if (isPremium) {
            val savedRemote = repository.save(movWithId)
            val entity = mapper.toMovementsEntity(savedRemote).apply { sync = true }
            dao.save(entity)
            savedRemote
        } else {
            val entity = mapper.toMovementsEntity(movWithId).apply { sync = false }
            dao.save(entity)
            mapper.toMovements(dao.findByUUID(entity.uuid))
        }
    }
}
