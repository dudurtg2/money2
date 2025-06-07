package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.UsersMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import com.tcc.money.data.services.AuthenticateService
import com.tcc.money.data.services.IsApiAvailableNowService
import com.tcc.money.data.services.NetworkIsConnectedService
import com.tcc.money.database.dao.UsersDao
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val usersMapper: UsersMapper,
    private val usersDao: UsersDao,
    private val networkIsConnectedService: NetworkIsConnectedService,
    private val isApiAvailableNowService: IsApiAvailableNowService,
    @ApplicationContext private val context: Context
) {

    suspend fun execute(login: Login): Users = withContext(Dispatchers.IO) {
        if (!networkIsConnectedService.isConnected(context)) {
            throw Exception("No internet connection")
        }
        val users = usersRepository.login(login)
        val entity = usersMapper.toUsersEntity(users)
        usersDao.save(entity)
        val usersEntity = usersDao.findByUUID(users.uuid)
        if (users.uuid == usersEntity.uuid) {
            users
        } else {
            throw Exception("Erro no login: UUID divergente")
        }
    }

    suspend fun logouf(){
        AuthenticateService.clearToken(context)
    }
}