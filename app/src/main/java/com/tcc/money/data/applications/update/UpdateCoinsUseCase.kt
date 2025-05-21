package com.tcc.money.data.applications.update

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UpdateCoinsUseCase(
    private val repository: CoinsRepository,
    private val dao: CoinsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: CoinsMapper
) {
    suspend fun execute(input: Coins): Coins =
        withContext(Dispatchers.IO) {
            val isPremium = checkPremium.execute()

            return@withContext if (isPremium) {
                val updatedRemote = repository.update(input.uuid, input)
                val entity = mapper.toCoinsEntity(updatedRemote).apply { sync = true }
                dao.update(entity)
                updatedRemote
            } else {
                val entity = mapper.toCoinsEntity(input.copy(uuid = input.uuid))
                    .apply { sync = false }
                dao.update(entity)
                mapper.toCoins(dao.findByUUID(entity.uuid))
            }
        }
}

