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
            Log.d("LoginUseCase", "Network is not connected")
            throw Exception("No internet connection")
        }

        if (!isApiAvailableNowService.execute(context)) {
            Log.d("LoginUseCase", "API is not available")
            throw Exception("API is not available")
        }

        Log.d("LoginUseCase", "Iniciando login para o usuário: ${login.login}")

        val users = usersRepository.login(login)
        Log.d("LoginUseCase", "Login bem-sucedido. Usuário retornado: ${users.uuid}")

        val entity = usersMapper.toUsersEntity(users)
        Log.d("LoginUseCase", "Usuário salvo no banco local: ${entity.uuid}")
        usersDao.save(entity)

        val usersEntity = usersDao.findByUUID(users.uuid)
        Log.d("LoginUseCase", "Usuário recuperado do banco local: ${usersEntity.uuid}")

        if (users.uuid == usersEntity.uuid) {
            Log.d("LoginUseCase", "UUID confirmado. Login validado com sucesso.")
            users
        } else {
            Log.e(
                "LoginUseCase",
                "UUID divergente! users.uuid=${users.uuid}, usersEntity.uuid=${usersEntity.uuid}"
            )
            throw Exception("Erro no login: UUID divergente")
        }
    }
}