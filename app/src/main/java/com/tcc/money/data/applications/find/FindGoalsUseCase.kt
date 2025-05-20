package com.tcc.money.data.applications.find

import android.content.Context
import com.tcc.money.data.applications.CheckPremiumAccountUseCase
import com.tcc.money.data.models.Goals
import com.tcc.money.data.repositories.GoalsRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.GoalsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class FindGoalsUseCase(context: Context) {
    private val goalsRepository = GoalsRepository(context)
    private val goalsDao = DataBase.getDatabase(context).goalsDao()
    private val hasPremiumAccountUseCase = CheckPremiumAccountUseCase(context).execute()
    private val goalsMapper = GoalsMapper()

    suspend fun execute(): List<Goals> = withContext(Dispatchers.IO) {
         if (hasPremiumAccountUseCase) {
            goalsRepository.findAll()
        } else {
            goalsMapper.toGoalsList(goalsDao.findAll())
        }
    }

    suspend fun execute(uuid: UUID): Goals = withContext(Dispatchers.IO) {
         if (hasPremiumAccountUseCase) {
            goalsRepository.findByUUID(uuid)
        } else {
            goalsMapper.toGoals(goalsDao.findByUUID(uuid))
        }
    }
}