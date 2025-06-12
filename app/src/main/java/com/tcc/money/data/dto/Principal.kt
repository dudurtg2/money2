package com.tcc.money.data.dto

import java.io.Serializable

data class PrincipalDTO(
    val nomeUsuario: String,
    val saldoAtual: String,
    val entradaValores: String,
    val filtroPeriodo: String,
    val bancoNome: String,
    val bancoValor: String,
    val ultimaMovimentacaoNome: String,
    val ultimaMovimentacaoCategoria: String,
    val ultimaMovimentacaoData: String,
    val ultimaMovimentacaoHora: String,
    val ultimaMovimentacaoValor: String,
    val origemBanco: String
) : Serializable
