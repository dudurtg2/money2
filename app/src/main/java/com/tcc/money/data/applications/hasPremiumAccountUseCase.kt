package com.tcc.money.data.applications

import com.tcc.money.data.local.entities.UsersLocal
import com.tcc.money.data.local.repositories.UsersRepositoryLocal
import com.tcc.money.utils.enums.TypeAccount

class hasPremiumAccountUseCase {
    private val usersRepositoryLocal = UsersRepositoryLocal()

    fun execute(): Boolean {
        if (usersRepositoryLocal.get().type.equals(TypeAccount.PREMIUM)) {
            return true
        } else {
            return false
        }
    }
}