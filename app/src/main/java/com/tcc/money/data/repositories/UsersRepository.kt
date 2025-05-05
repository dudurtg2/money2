package com.tcc.money.data.repositories

import com.tcc.money.data.Intefaces.IUsersRepository
import com.tcc.money.data.models.Users
import com.tcc.money.network.retrofit.UsersRetrofit


class UsersRepository: IUsersRepository {
    private val api = UsersRetrofit.instance

    suspend override fun save(users: Users): Users {
       return api.save(users)
    }

    suspend  override fun login(users: Users): Users {
        return api.login(users)
    }

    suspend  override fun findById(id: Long): Users {
        return api.findById(id)
    }

    suspend  override fun update(users: Users): Users {
        return api.update(users)
    }


}