package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.database.DataBase
import com.tcc.money.utils.enums.TypeAccount

class CheckPremiumAccountUseCase(context: Context) {
    private val usersDao = DataBase.getDatabase(context).usersDao()

    fun execute(): Boolean {
        if (usersDao.findById(1).type.equals(TypeAccount.PREMIUM)) {
            return true
        } else {
            return false
        }
    }
}