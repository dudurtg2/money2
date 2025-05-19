package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers
import java.util.UUID

class SaveMovementsUseCase(context: Context) {
    private val movementsRepository = MovementsRepository(context)
    private val movementsDao = DataBase.getDatabase(context).movementsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val movementsMapper = MovementsMapper()
     suspend fun execute(movements: Movements): Movements = withContext(Dispatchers.IO)  {
         movements.uuid = UUID.randomUUID()
        if (hasPremiumAccountUseCase) {
             movementsRepository.save(movements)
        } else {
            val movementsEntity = movementsMapper.toMovementsEntity(movements)
            movementsEntity.sync = false
            movementsDao.save(
                movementsEntity
            )
             movementsMapper.toMovements(
                 movementsDao.findByUUID(
                     movementsEntity.uuid
                )
            )
        }
    }
}