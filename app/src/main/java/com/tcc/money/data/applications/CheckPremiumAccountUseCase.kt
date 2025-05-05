package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.enums.TypeAccount

class CheckPremiumAccountUseCase(context: Context) {
    private val usersDao = DataBase.getDatabase(context).usersDao()
    private val usersRepository = UsersRepository()

    fun execute(): Boolean {
        if (usersRepository.findById(usersDao.findById(1).id).type.equals(TypeAccount.PREMIUM)) {
            return true
        } else {
            return false
        }
    }
}