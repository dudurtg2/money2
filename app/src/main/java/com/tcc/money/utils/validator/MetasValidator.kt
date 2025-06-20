package com.tcc.money.utils.validator

object MetasValidator {

    fun validarNome(nome: String): Boolean = nome.isNotBlank()

    fun validarValor(valor: Double): Boolean = valor > 0.0

    fun validarData(data: String): Boolean = data.isNotBlank()

    fun validarDescricao(descricao: String): Boolean = descricao.isNotBlank()

    fun validarTodos(
        nome: String,
        valor: Double,
        data: String,
        descricao: String
    ): Boolean {
        return validarNome(nome) &&
                validarValor(valor) &&
                validarData(data) &&
                validarDescricao(descricao)
    }
}
