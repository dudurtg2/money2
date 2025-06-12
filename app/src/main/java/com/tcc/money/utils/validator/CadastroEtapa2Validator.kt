package com.tcc.money.utils.validator

object CadastroEtapa2Validator {

    fun validarEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    fun validarSenha(senha: String): Boolean {
        return senha.length >= 6
    }

    fun validarConfirmacao(senha: String, confirmarSenha: String): Boolean {
        return senha == confirmarSenha
    }

    fun validarTodos(email: String, senha: String, confirmarSenha: String): Boolean {
        return validarEmail(email) &&
                validarSenha(senha) &&
                validarConfirmacao(senha, confirmarSenha)
    }
}

