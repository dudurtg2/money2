package com.tcc.money.data.applications.update

import android.content.Context
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import javax.inject.Inject

class UpdateUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    fun execute(users: Users): Users {
        return usersRepository.update(users)
    }
}