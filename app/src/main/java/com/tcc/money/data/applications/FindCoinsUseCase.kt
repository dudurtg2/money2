package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.CoinsMapper
import org.mapstruct.factory.Mappers

class FindCoinsUseCase(context: Context) {
    private val coinsRepository = CoinsRepository()
    private val coinsDao = DataBase.getDatabase(context).coinsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val coinsMapper = Mappers.getMapper(CoinsMapper::class.java)

    suspend fun execute(): List<Coins> {
        if (hasPremiumAccountUseCase) {
            return coinsRepository.findAll()
        } else {
            return coinsMapper.toCoinsList(coinsDao.findAll())
        }
    }

    suspend  fun execute(index: Long): Coins {
        if (hasPremiumAccountUseCase) {
            return coinsRepository.findById(index)
        } else {
            return coinsMapper.toCoins(coinsDao.findByid(index))
        }
    }
}