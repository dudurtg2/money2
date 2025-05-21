package com.tcc.money.data.applications.save

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Goals
import com.tcc.money.data.repositories.GoalsRepository
import com.tcc.money.database.dao.GoalsDao
import com.tcc.money.utils.mapper.GoalsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class SaveGoalsUseCase(
    private val repository: GoalsRepository,
    private val dao: GoalsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: GoalsMapper
) {
    suspend fun execute(input: Goals): Goals = withContext(Dispatchers.IO) {

        val goalsWithId = input.copy(uuid = UUID.randomUUID())
        val isPremium = checkPremium.execute()

        return@withContext if (isPremium) {
            val savedRemote = repository.save(goalsWithId)
            val entity = mapper.toGoalsEntity(savedRemote).apply { sync = true }
            dao.save(entity)
            savedRemote
        } else {
            val entity = mapper.toGoalsEntity(goalsWithId).apply { sync = false }
            dao.save(entity)
            mapper.toGoals(dao.findByUUID(entity.uuid))
        }
    }
}

