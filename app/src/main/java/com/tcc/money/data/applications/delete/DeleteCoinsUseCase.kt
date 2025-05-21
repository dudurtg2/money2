package com.tcc.money.data.applications.delete

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class DeleteCoinsUseCase(
    private val repository: CoinsRepository,
    private val dao: CoinsDao,
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

