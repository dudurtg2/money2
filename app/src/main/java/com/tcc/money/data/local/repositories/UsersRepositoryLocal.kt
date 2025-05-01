package com.tcc.money.data.local.repositories

import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.api.models.Users
import com.tcc.money.data.local.entities.UsersLocal
import com.tcc.money.utils.enums.TypeAccount

class UsersRepositoryLocal: IUsersRepository {
    override fun save(users: Users): Users {
        TODO("Not yet implemented")
    }

    override fun login(users: Users): Users {
        return users
    }

    fun get(): UsersLocal {
        return UsersLocal("","","","", TypeAccount.NORMAL)
        }
    }