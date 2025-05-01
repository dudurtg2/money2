package com.tcc.money.data.local.entities

import com.tcc.money.utils.enums.TypeAccount


data class UsersLocal(
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val type: TypeAccount
)
