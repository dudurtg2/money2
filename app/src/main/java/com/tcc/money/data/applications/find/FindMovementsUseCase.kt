package com.tcc.money.data.applications.find

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FindMovementsUseCase(
    private val repository: MovementsRepository,
    private val dao: MovementsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: MovementsMapper
) {

    suspend fun executeAll(): List<Movements> = withContext(Dispatchers.IO) {
        val isPremium = checkPremium.execute()

        if (isPremium) {
            val remoteList = repository.findAll()
            val entities = mapper
                .toMovementsEntityList(remoteList)
                .onEach { it.sync = true }
            dao.saveAll(entities)
            remoteList
        } else {
            mapper.toMovementsList(dao.findAll())
        }
    }

    suspend fun executeById(uuid: UUID): Movements = withContext(Dispatchers.IO) {
        val isPremium = checkPremium.execute()

        if (isPremium) {
            val remote = repository.findByUUID(uuid)
            val entity = mapper.toMovementsEntity(remote).apply { sync = true }
            dao.update( entity)
            remote
        } else {
            mapper.toMovements(dao.findByUUID(uuid))
        }
    }
}
