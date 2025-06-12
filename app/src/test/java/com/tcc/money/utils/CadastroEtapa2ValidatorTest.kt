package com.tcc.money.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CadastroEtapa2ValidatorTest {

    @Test
    fun `senha curta deve ser rejeitada`() {
        assertFalse(CadastroEtapa2Validator.validarSenha("123"))
    }

    @Test
    fun `confirmação de senha deve ser igual à senha`() {
        assertTrue(CadastroEtapa2Validator.validarConfirmacao("senha123", "senha123"))
        assertFalse(CadastroEtapa2Validator.validarConfirmacao("senha123", "senha321"))
    }

    @Test
    fun `todos os campos válidos devem retornar verdadeiro`() {
        val resultado = CadastroEtapa2Validator.validarTodos(
            email = "usuario@email.com",
            senha = "senha123",
            confirmarSenha = "senha123"
        )
        assertTrue(resultado)
    }

    @Test
    fun `campo inválido deve fazer validação total falhar`() {
        val resultado = CadastroEtapa2Validator.validarTodos(
            email = "",
            senha = "123",
            confirmarSenha = "456"
        )
        assertFalse(resultado)
    }
}

