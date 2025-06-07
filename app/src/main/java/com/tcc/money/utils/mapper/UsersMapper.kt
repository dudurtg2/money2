package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Users
import com.tcc.money.database.entities.UsersEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersMapper @Inject constructor(){

    fun toUsers(entity: UsersEntity): Users {
        return Users(
            uuid = entity.uuid,
            nome = entity.nome,
            email = entity.email,
            type = entity.type,
            cpf = entity.cpf,
            genero = entity.genero,
            telefone = entity.telefone,
            dataNascimento = entity.dataNascimento
        )
    }

    fun toUsersEntity(entity: Users): UsersEntity {
        return UsersEntity(
            uuid = entity.uuid,
            nome = entity.nome,
            email = entity.email,
            type = entity.type,
            cpf = entity.cpf,
            genero = entity.genero,
            telefone = entity.telefone,
            dataNascimento = entity.dataNascimento,
            sync = false
        )
    }

    fun toUsersEntityList(users: List<Users>): List<UsersEntity> {
        return users.map { toUsersEntity(it) }
    }

    fun toUsersList(entities: List<UsersEntity>): List<Users> {
        return entities.map { toUsers(it) }
    }
}
