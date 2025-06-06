package com.tcc.money.data.applications.update

import android.content.Context
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Movements
import com.tcc.money.data.repositories.MovementsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.database.dao.MovementsDao
import com.tcc.money.utils.mapper.MovementsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class UpdateMovementsUseCase @Inject constructor(
    private val repository: MovementsRepository,
    private val dao: MovementsDao,
    private val checkPremium: CheckPremiumAccountUseCase,
    private val mapper: MovementsMapper
) {
    suspend fun execute(movements: Movements): Movements =
        withContext(Dispatchers.IO) {
            val isPremium = checkPremium.execute()
            if (isPremium) {
                val updated = repository.update(movements.uuid, movements)
                val entity = mapper.toMovementsEntity(updated).apply { sync = true }
                dao.update(entity)
                updated
            } else {
                val entity = mapper.toMovementsEntity(movements).apply { sync = false }
                dao.update(entity)
                mapper.toMovements(dao.findByUUID(movements.uuid))
            }
        }
}
