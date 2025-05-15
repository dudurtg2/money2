package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Coins
import com.tcc.money.data.repositories.CoinsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.CoinsMapper
import org.mapstruct.factory.Mappers

class SaveCoinsUseCase(context: Context) {
    private val coinsRepository = CoinsRepository(context)
    private val coinsDao = DataBase.getDatabase(context).coinsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val coinsMapper = Mappers.getMapper(CoinsMapper::class.java)

    suspend fun execute(coins: Coins): Coins {
        return if (hasPremiumAccountUseCase) {
            coinsRepository.save(coins)
        } else {
            val coinsEntity = coinsMapper.toCoinsEntity(coins)
            coinsEntity.sync = false
            coinsDao.save(coinsEntity)
            coinsRepository.findByUUID(coinsEntity.uuid)
        }
    }
}
