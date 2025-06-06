package com.tcc.money.data.applications.save

import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class SaveCoinsUseCase @Inject constructor(
    private val repository: CoinsRepository,
    private val dao: CoinsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: CoinsMapper
) {
    suspend fun execute(input: Coins): Coins = withContext(Dispatchers.IO) {
        val coinsWithId = input.copy(uuid = UUID.randomUUID())
        val isPremium = checkPremium.execute()
        if (isPremium) {
            val savedRemote = repository.save(coinsWithId)
            val entity = mapper.toCoinsEntity(savedRemote).apply {
                sync = true
            }
            dao.save(entity)
            savedRemote
        } else {
            val entity = mapper.toCoinsEntity(coinsWithId).apply {
                sync = false
            }
            dao.save(entity)
            mapper.toCoins(dao.findByUUID(entity.uuid))
        }
    }
}

