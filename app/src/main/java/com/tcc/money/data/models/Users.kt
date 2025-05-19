package com.tcc.money.data.models

import com.tcc.money.utils.enums.TypeAccount
import java.time.LocalDateTime
import java.util.UUID


data class Users(
    var uuid: UUID,
    var nome: String,
    var dataNascimento: LocalDateTime,
    var genero: String,
    var email: String,
    var cpf: String,
    var telefone: String,
    var type: TypeAccount
)
