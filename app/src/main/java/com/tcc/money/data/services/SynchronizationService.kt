package com.tcc.money.data.services

import android.content.Context
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.database.exception.SyncErrorException
import com.tcc.money.utils.mapper.CoinsMapper
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SynchronizationService(context: Context) {
    private val db = DataBase.getDatabase(context)
    private val checkPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()

    private val coinsMapper = CoinsMapper()
    private val movementsMapper = MovementsMapper()
    private val isNetworkAvailableService = NetworkIsConnectedService().isConnected(context)
    private val coinsDao = db.coinsDao()
    private val movementsDao = db.movementsDao()
    private val refreshTokenService = RefreshTokenService(context)
    private val coinsRepository = CoinsRepository(context)
    private val movementsRepository = MovementsRepository(context)


    suspend fun execute(): Boolean = withContext(Dispatchers.IO) {
        refreshTokenService.execute()
        if (!isNetworkAvailableService) {
            return@withContext false
        }
        if (!checkPremiumAccountUseCase) {
            return@withContext false
        }


        try {
            val coinsToSync = coinsDao.findByNotSync().map(coinsMapper::toCoins)
            val movesToSync = movementsDao.findByNotSync().map(movementsMapper::toMovements)

            syncCoinsToApi(coinsToSync)
            syncMovementsToApi(movesToSync)
            true
        } catch (e: Exception) {
            throw SyncErrorException(cause = e)
        }
    }

    private suspend fun syncCoinsToApi(coins: List<Coins>) = withContext(Dispatchers.IO) {
        for (coin in coins) {
            if (coinsRepository.save(coin) != null) {
                var entity = coinsMapper.toCoinsEntity(coin).apply { sync = true;}
                coinsDao.update(entity)
            }
        }
    }

    private suspend fun syncMovementsToApi(movements: List<Movements>) = withContext(Dispatchers.IO) {
        for (movement in movements) {
            if (movementsRepository.save(movement) != null) {
                var entity = movementsMapper.toMovementsEntity(movement).apply { sync = true }
                movementsDao.update(entity)
            }
        }
    }
}
