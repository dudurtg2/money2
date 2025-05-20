package com.tcc.money.data.applications.find

import android.content.Context
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FindMovementsUseCase(context: Context) {
    private val movementsRepository = MovementsRepository(context)
    private val movementsDao = DataBase.getDatabase(context).movementsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val movementsMapper = MovementsMapper()

    suspend fun execute(): List<Movements> = withContext(Dispatchers.IO) {
         if (hasPremiumAccountUseCase) {
            movementsRepository.findAll()
        } else {
            movementsMapper.toMovementsList(movementsDao.findAll())
        }
    }

    suspend fun execute(uuid: UUID): Movements = withContext(Dispatchers.IO) {
         if (hasPremiumAccountUseCase) {
            movementsRepository.findByUUID(uuid)
        } else {
            movementsMapper.toMovements(movementsDao.findByUUID(uuid))
        }
    }
}