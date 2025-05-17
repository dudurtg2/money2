package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveCoinsUseCase(context: Context) {
    private val coinsRepository = CoinsRepository(context)
    private val coinsDao = DataBase.getDatabase(context).coinsDao()
    private val hasPremiumAccountUseCase = false
    private val coinsMapper = CoinsMapper()


    suspend fun execute(coins: Coins): Coins = withContext(Dispatchers.IO) {
        if (hasPremiumAccountUseCase) {
            coinsRepository.save(coins)
        } else {
            val coinsEntity = coinsMapper.toCoinsEntity(coins).apply {
                sync = false
            }

            coinsDao.save(coinsEntity)
            coinsMapper.toCoins(
                coinsDao.findByUUID(
                    coinsEntity.uuid
                )
            )
        }
    }
}
