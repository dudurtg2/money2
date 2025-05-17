package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.CoinsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FindCoinsUseCase(context: Context) {
    private val coinsRepository = CoinsRepository(context)
    private val coinsDao = DataBase.getDatabase(context).coinsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val coinsMapper = CoinsMapper()


    suspend fun execute(): List<Coins> = withContext(Dispatchers.IO) {
        if (hasPremiumAccountUseCase) {
            coinsRepository.findAll()
        } else {
            coinsMapper.toCoinsList(coinsDao.findAll())
        }
    }

    suspend fun execute(index: UUID): Coins = withContext(Dispatchers.IO) {
        if (hasPremiumAccountUseCase) {
            coinsRepository.findByUUID(index)
        } else {
            coinsMapper.toCoins(coinsDao.findByUUID(index))
        }
    }
}
