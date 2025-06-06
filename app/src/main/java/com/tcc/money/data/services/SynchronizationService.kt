package com.tcc.money.data.services

import android.content.Context
import android.util.Log
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.dao.CoinsDao
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.database.exception.SyncErrorException
import com.tcc.money.utils.mapper.CoinsMapper
import com.tcc.money.utils.mapper.MovementsMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SynchronizationService @Inject constructor(
    private val checkPremiumAccountUseCase: CheckPremiumAccountUseCase,
    private val networkIsConnectedService: NetworkIsConnectedService,
    private val refreshTokenService: RefreshTokenService,
    private val coinsDao: CoinsDao,
    private val movementsDao: MovementsDao,
    private val coinsRepository: CoinsRepository,
    private val movementsRepository: MovementsRepository,
    private val coinsMapper: CoinsMapper,
    private val movementsMapper: MovementsMapper,
    @ApplicationContext private val context: Context
) {

    suspend fun execute(): Boolean = withContext(Dispatchers.IO) {

        refreshTokenService.execute()

        if (!networkIsConnectedService.isConnected(context)) {
            return@withContext false
        }

        val isPremium = checkPremiumAccountUseCase.execute()
        if (!isPremium) {
            return@withContext false
        }

        try {
            val coinsToSync: List<Coins> =
                coinsDao.findByNotSync().map(coinsMapper::toCoins)

            val movesToSync: List<Movements> =
                movementsDao.findByNotSync().map(movementsMapper::toMovements)

            syncCoinsToApi(coinsToSync)
            syncMovementsToApi(movesToSync)
            true
        } catch (e: Exception) {
            throw SyncErrorException(cause = e)
        }
    }

    private suspend fun syncCoinsToApi(coins: List<Coins>) = withContext(Dispatchers.IO) {
        for (coin in coins) {
            val saved = coinsRepository.save(coin)
            if (saved != null) {
                val entity = coinsMapper.toCoinsEntity(coin).apply { sync = true }
                coinsDao.update(entity)
            }
        }
    }

    private suspend fun syncMovementsToApi(movements: List<Movements>) = withContext(Dispatchers.IO) {
        for (movement in movements) {
            val saved = movementsRepository.save(movement)
            if (saved != null) {
                val entity = movementsMapper.toMovementsEntity(movement).apply { sync = true }
                movementsDao.update(entity)
            }
        }
    }
}
