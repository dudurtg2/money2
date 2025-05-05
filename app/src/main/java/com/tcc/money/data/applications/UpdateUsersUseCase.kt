package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase

class UpdateUsersUseCase(context: Context) {
    private val usersRepository = UsersRepository()
    private val usersDao = DataBase.getDatabase(context).usersDao()


    suspend fun execute(users: Users): Users {
        return usersRepository.update(users)
    }

}