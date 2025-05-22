package com.tcc.money.data.applications.find

import android.content.Context
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.data.services.SynchronizationService
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FindCoinsUseCase(
    private val repository: CoinsRepository,
    private val dao: CoinsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: CoinsMapper,
    private val context: Context
) {

    suspend fun executeAll(): List<Coins> = withContext(Dispatchers.IO) {

        val isPremium = checkPremium.execute()
        if (isPremium) {
            SynchronizationService(context).execute()
            val remoteList = repository.findAll()
            val entities = mapper.toCoinsEntityList(remoteList)
                .onEach { it.sync = true }
            dao.saveAll(entities)
            remoteList
        } else {
            mapper.toCoinsList(dao.findAll())
        }
    }

    suspend fun executeById(uuid: UUID): Coins = withContext(Dispatchers.IO) {
        val isPremium = checkPremium.execute()
        if (isPremium) {
            SynchronizationService(context).execute()
            val remote = repository.findByUUID(uuid)
            val entity = mapper.toCoinsEntity(remote).apply { sync = true }
            dao.save(entity)
            remote
        } else {
            mapper.toCoins(dao.findByUUID(uuid))
        }
    }
}

