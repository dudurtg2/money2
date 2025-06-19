package com.tcc.money.data.dto

import java.io.Serializable

data class LancamentoDespesaDTO(
    val valor: String,
    val tipo: String,
    val origem: String,
    val categoria: String,
    val descricao: String? = null,
    val recorrente: Boolean = false,
    val parcelado: Boolean = false,
    val qtdParcelas: Int? = null
) : Serializable
