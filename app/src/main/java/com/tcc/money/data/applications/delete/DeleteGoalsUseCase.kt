package com.tcc.money.data.applications.delete

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Goals
import com.tcc.money.data.repositories.GoalsRepository
import com.tcc.money.database.dao.GoalsDao
import com.tcc.money.utils.mapper.GoalsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class DeleteGoalsUseCase(
    private val repository: GoalsRepository,
    private val dao: GoalsDao,
    private val checkPremium: CheckPremiumAccountUseCase
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


