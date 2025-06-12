object CadastroValidator {

    fun isNomeValido(nome: String): Boolean {
        return nome.isNotBlank()
    }

    fun isSobrenomeValido(sobrenome: String): Boolean {
        return sobrenome.isNotBlank()
    }

    fun isCpfValido(cpf: String): Boolean {
        return cpf.length == 11 && cpf.all { it.isDigit() }
    }

    fun isDataNascimentoValida(dataNascimento: String): Boolean {
        return dataNascimento.isNotBlank()
    }

    fun validarTodos(nome: String, sobrenome: String, cpf: String, dataNascimento: String): Boolean {
        return isNomeValido(nome) &&
                isSobrenomeValido(sobrenome) &&
                isCpfValido(cpf) &&
                isDataNascimentoValida(dataNascimento)
    }
}

