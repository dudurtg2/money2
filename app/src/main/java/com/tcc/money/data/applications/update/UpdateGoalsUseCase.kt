package com.tcc.money.data.applications.update

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Goals
import com.tcc.money.data.repositories.GoalsRepository
import com.tcc.money.database.dao.GoalsDao
import com.tcc.money.utils.mapper.GoalsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UpdateGoalsUseCase(
    private val repository: GoalsRepository,
    private val dao: GoalsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: GoalsMapper
) {
    suspend fun execute(input: Goals): Goals =
        withContext(Dispatchers.IO) {
            val isPremium = checkPremium.execute()
            return@withContext if (isPremium) {
                val updatedRemote = repository.update(input.uuid, input)
                val entity = mapper.toGoalsEntity(updatedRemote).apply { sync = true }
                dao.update(entity)
                updatedRemote
            } else {
                val entity = mapper.toGoalsEntity(input.copy(uuid = input.uuid))
                    .apply { sync = false }
                dao.update(entity)
                mapper.toGoals(dao.findByUUID(input.uuid))
            }
        }
}
