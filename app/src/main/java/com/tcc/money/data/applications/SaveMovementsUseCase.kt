package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.MovementsMapper
import org.mapstruct.factory.Mappers

class SaveMovementsUseCase(context: Context) {
    private val movementsRepository = MovementsRepository()
    private val movementsDao = DataBase.getDatabase(context).movementsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val movementsMapper = Mappers.getMapper(MovementsMapper::class.java)
    suspend fun execute(movements: Movements): Movements {
        if (hasPremiumAccountUseCase) {
            return movementsRepository.save(movements)
        } else {
            val movementsEntity = movementsMapper.toMovementsEntity(movements)
            movementsEntity.sync = false
            return movementsMapper.toMovements(
                movementsDao.save(
                    movementsEntity
                )
            )
        }
    }
}