package com.tcc.money.utils.validator

object LaunchValidator {

    // Verifica se o valor é válido (não vazio, numérico e maior que zero)
    fun isValorValido(valor: String): Boolean {
        if (valor.isBlank()) return false

        val valorFormatado = valor.replace(",", ".")
        val numero = valorFormatado.toDoubleOrNull()

        return numero != null && numero > 0.0
    }

    // Valida todos os campos (nesse caso, só o valor mesmo)
    fun validarTodos(valor: String): Boolean {
        return isValorValido(valor)
    }
}
