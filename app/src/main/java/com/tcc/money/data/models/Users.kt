package com.tcc.money.data.models

import com.tcc.money.utils.enums.TypeAccount
import java.time.LocalDateTime
import java.util.UUID


data class Users(
    val uuid: UUID,
    val nome: String,
    val dataNascimento: LocalDateTime,
    val genero: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val type: TypeAccount
)
