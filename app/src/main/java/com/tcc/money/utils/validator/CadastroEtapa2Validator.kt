package com.tcc.money.utils.validator

object CadastroEtapa2Validator {

    fun isEmailValido(email: String): Boolean {
        return email.isNotBlank() &&
                email.contains("@") &&
                email.contains(".")
    }

    fun isSenhaValida(senha: String): Boolean {
        return senha.isNotBlank() && senha.length >= 6
    }

    fun isConfirmacaoSenhaValida(senha: String, confirmarSenha: String): Boolean {
        return senha == confirmarSenha && confirmarSenha.isNotBlank()
    }

    fun validarTodos(
        email: String,
        senha: String,
        confirmarSenha: String
    ): Boolean {
        return isEmailValido(email) &&
                isSenhaValida(senha) &&
                isConfirmacaoSenhaValida(senha, confirmarSenha)
    }
}
