package com.tcc.money.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcc.money.utils.enums.TypeAccount

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey
    val id: Long,
    val sync: Boolean,

    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val type: TypeAccount
)
