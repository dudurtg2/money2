package com.tcc.money.data.repositories

import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Users


class UsersRepository: IUsersRepository {
    override fun save(users: Users): Users {
        TODO("Not yet implemented")
    }

    override fun login(users: Users): Users {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Users {
        TODO("Not yet implemented")
    }


}