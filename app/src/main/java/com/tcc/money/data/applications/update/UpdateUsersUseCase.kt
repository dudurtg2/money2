package com.tcc.money.data.applications.update

import android.content.Context
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase

class UpdateUsersUseCase(context: Context) {
    private val usersRepository = UsersRepository(context)
    private val usersDao = DataBase.getDatabase(context).usersDao()


     fun execute(users: Users): Users {
        return usersRepository.update(users)
    }

}