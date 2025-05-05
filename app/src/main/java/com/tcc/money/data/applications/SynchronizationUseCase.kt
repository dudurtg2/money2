package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import com.tcc.money.database.exception.SyncErrorException
import com.tcc.money.utils.mapper.CoinsMapper
import com.tcc.money.utils.mapper.MovementsMapper
import org.mapstruct.factory.Mappers

class SynchronizationUseCase(context: Context) {
    private val db = DataBase.getDatabase(context)
    private val checkPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()

    private val coinsMapper = Mappers.getMapper(CoinsMapper::class.java)
    private val movementsMapper = Mappers.getMapper(MovementsMapper::class.java)

    private val coinsDao = db.coinsDao()
    private val movementsDao = db.movementsDao()
    private val coinsRepository = CoinsRepository()
    private val movementsRepository = MovementsRepository()

    suspend fun execute(): Boolean {
        if (!checkPremiumAccountUseCase) return false
        try {
            val coins: List<Coins> = coinsMapper.toCoinsList(coinsDao.findByNotSync())
            val movements: List<Movements> =
                movementsMapper.toMovementsList(movementsDao.findByNotSync())
            syncCoinsToApi(coins)
            syncMovementsToApi(movements)
            return true
        } catch (e: Exception) {
            throw SyncErrorException(cause = e)
        }
    }

    suspend fun syncCoinsToApi(coins: List<Coins>) {
        for (coin in coins) {
            coinsRepository.save(coin)
        }
    }

    suspend fun syncMovementsToApi(movements: List<Movements>) {
        for (movement in movements) {
            movementsRepository.save(movement)
        }
    }
}