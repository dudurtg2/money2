package com.tcc.money.data.applications.find

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Goals
import com.tcc.money.data.repositories.GoalsRepository
import com.tcc.money.database.dao.GoalsDao
import com.tcc.money.utils.mapper.GoalsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FindGoalsUseCase(
    private val repository: GoalsRepository,
    private val dao: GoalsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: GoalsMapper
) {

    suspend fun executeAll(): List<Goals> = withContext(Dispatchers.IO) {
        val isPremium = checkPremium.execute()
        if (isPremium) {
            val remoteList = repository.findAll()
            val entities = mapper.toGoalsEntityList(remoteList)
                .onEach { it.sync = true }
            dao.saveAll(entities)
            remoteList
        } else {
            mapper.toGoalsList(dao.findAll())
        }
    }

    suspend fun executeById(uuid: UUID): Goals = withContext(Dispatchers.IO) {
        val isPremium = checkPremium.execute()
        if (isPremium) {
            val remote = repository.findByUUID(uuid)
            val entity = mapper.toGoalsEntity(remote).apply { sync = true }
            dao.update(entity)
            remote
        } else {
            mapper.toGoals(dao.findByUUID(uuid))
        }
    }
}
