package com.tcc.money.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tcc.money.utils.enums.TypeAccount
import java.time.LocalDateTime

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey
    val email: String,
    val sync: Boolean,

    val dataNascimento: LocalDateTime,
    val genero: String,
    val nome: String,
    val cpf: String,
    val telefone: String,
    val type: TypeAccount
)
