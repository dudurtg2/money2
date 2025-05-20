package com.tcc.money.data.applications.save

import android.content.Context
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Goals
import com.tcc.money.data.repositories.GoalsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.GoalsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class SaveGoalsUseCase(context: Context) {
    private val goalsRepository = GoalsRepository(context)
    private val goalsDao = DataBase.getDatabase(context).goalsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val goalsMapper = GoalsMapper()
     suspend fun execute(Goals: Goals): Goals = withContext(Dispatchers.IO)  {
         Goals.uuid = UUID.randomUUID()
        if (hasPremiumAccountUseCase) {
             goalsRepository.save(Goals)
        } else {
            val goalsEntity = goalsMapper.toGoalsEntity(Goals)
            goalsEntity.sync = false
            goalsDao.save(
                goalsEntity
            )
             goalsMapper.toGoals(
                 goalsDao.findByUUID(
                     goalsEntity.uuid
                )
            )
        }
    }
}