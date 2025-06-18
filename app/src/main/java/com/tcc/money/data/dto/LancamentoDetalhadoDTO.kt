package com.tcc.money.data.dto

import java.io.Serializable

data class LancamentoDetalhadoDTO(
    val valor: String,
    val tipo: String,
    val destino: String,
    val categoria: String,
    val descricao: String?,
    val recorrente: Boolean,
    val parcelado: Boolean,
    val qtdParcelas: Int?
) : Serializable

