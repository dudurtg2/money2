package com.tcc.money.utils.mapper

import com.tcc.money.data.models.Users
import com.tcc.money.database.entities.UsersEntity
import org.mapstruct.Mapper

@Mapper
interface UsersMapper {
    fun toUsers(usersEntity: UsersEntity): Users
    fun toUsersEntity(users: Users): UsersEntity
    fun toUsersEntityList(users: List<Users>): List<UsersEntity>
    fun toUsersList(usersEntity: List<UsersEntity>): List<Users>

}