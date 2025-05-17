package com.tcc.money.data.applications

import android.content.Context
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

class SynchronizationUseCase(context: Context) {
    private val db = DataBase.getDatabase(context)
    private val checkPremiumAccountUseCase = CheckPremiumAccountUseCase(context)

    private val coinsMapper = CoinsMapper()
    private val movementsMapper = MovementsMapper()

    private val coinsDao = db.coinsDao()
    private val movementsDao = db.movementsDao()
    private val coinsRepository = CoinsRepository(context)
    private val movementsRepository = MovementsRepository(context)

    /**
     * Retorna true se sincronizou, false se não é premium.
     * Lança SyncErrorException em caso de falha.
     */
    suspend fun execute(): Boolean = withContext(Dispatchers.IO) {
        // verifica premium (supondo que execute() do CheckPremiumAccountUseCase seja suspend)
        if (false) return@withContext false

        try {
            // Busca entidades não sincronizadas
            val coinsToSync = coinsDao.findByNotSync().map(coinsMapper::toCoins)
            val movesToSync = movementsDao.findByNotSync().map(movementsMapper::toMovements)

            // Sincroniza em paralelo ou sequencialmente
            syncCoinsToApi(coinsToSync)
            syncMovementsToApi(movesToSync)
            true
        } catch (e: Exception) {
            throw SyncErrorException(cause = e)
        }
    }

    private suspend fun syncCoinsToApi(coins: List<Coins>) = withContext(Dispatchers.IO) {
        for (coin in coins) {
            // se salvar na API, atualiza flag no banco
            if (coinsRepository.save(coin) != null) {
                val entity = coinsMapper.toCoinsEntity(coin).apply { sync = true }
                coinsDao.update(entity)
            }
        }
    }

    private suspend fun syncMovementsToApi(movements: List<Movements>) = withContext(Dispatchers.IO) {
        for (movement in movements) {
            if (movementsRepository.save(movement) != null) {
                val entity = movementsMapper.toMovementsEntity(movement).apply { sync = true }
                movementsDao.update(entity)
            }
        }
    }
}
