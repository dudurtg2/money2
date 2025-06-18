package com.tcc.money.utils.validator

object PrincipalValidator {

    fun isNomeUsuarioValido(nome: String): Boolean {
        return nome.isNotBlank()
    }

    fun isSaldoValido(saldo: Float): Boolean {
        return saldo >= 0f
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
        val textoFormatado = texto.lowercase()
        return textoFormatado.contains("semana") ||
                textoFormatado.contains("atual") ||
                textoFormatado.contains("mês") ||
                textoFormatado.contains("hoje")
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
