package com.tcc.money.utils.validator

object PrincipalValidator {

    fun isNomeUsuarioValido(nome: String): Boolean {
        return nome.isNotBlank()
    }

    fun isSaldoValido(saldo: Float): Boolean {
        return saldo > 0f
    }

    fun isEntradaValida(valor: Float): Boolean {
        return valor > 0f
    }

    fun isFiltroValido(filtro: String?): Boolean {
        return !filtro.isNullOrBlank()
    }

    fun isListaMovimentacoesValida(lista: List<String>?): Boolean {
        return !lista.isNullOrEmpty()
    }

    fun isTextoEntradaFormatado(texto: String): Boolean {
        return texto.startsWith("R$") && texto.length > 3
    }

    fun isTextoFiltroFormatado(texto: String): Boolean {
        return texto.contains("semana", ignoreCase = true) || texto.contains("atual", ignoreCase = true)
    }

    fun isValorBancoValido(valor: Float): Boolean {
        return valor >= 0f
    }

    fun validarTodos(
        nome: String,
        saldo: Float,
        entrada: Float,
        filtro: String?,
        textoEntrada: String,
        textoFiltro: String?,
        listaMovimentacoes: List<String>?,
        valorBanco: Float
    ): Boolean {
        return isNomeUsuarioValido(nome) &&
                isSaldoValido(saldo) &&
                isEntradaValida(entrada) &&
                isFiltroValido(filtro) &&
                isTextoEntradaFormatado(textoEntrada) &&
                isTextoFiltroFormatado(textoFiltro ?: "") &&
                isListaMovimentacoesValida(listaMovimentacoes) &&
                isValorBancoValido(valorBanco)
    }
}
