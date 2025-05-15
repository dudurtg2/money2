package com.tcc.money.data.applications

import android.content.Context
import com.tcc.money.data.dto.Login
import com.tcc.money.data.models.Users
import com.tcc.money.data.repositories.UsersRepository
import com.tcc.money.database.DataBase
import com.tcc.money.utils.mapper.UsersMapper
import org.mapstruct.factory.Mappers

class LoginUseCase(context: Context) {
    private val db = DataBase.getDatabase(context)
    private val usersRepository = UsersRepository(context)
    private val usersMapper = Mappers.getMapper(UsersMapper::class.java)
    private val usersDao = db.usersDao()


   suspend fun execute(login: Login): Users {


       val users = usersRepository.login(login)
       usersDao.save(usersMapper.toUsersEntity(users))
       val usersEntity = usersDao.findByUUID(users.uuid)



       if (users.uuid == usersEntity.uuid) {
           return users
       } else {
           throw Exception("Erro no login: nome divergente")
       }


   }

}