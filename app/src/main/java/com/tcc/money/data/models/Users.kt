package com.tcc.money.data.models

import com.tcc.money.utils.enums.TypeAccount


data class Users(
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val type: TypeAccount
)
