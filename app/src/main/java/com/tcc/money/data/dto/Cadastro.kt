package com.tcc.money.data.dto

import java.io.Serializable

data class Cadastro(
    val nome: String,
    val sobrenome: String,
    val cpf: String,
    val dataNascimento: String
) : Serializable
