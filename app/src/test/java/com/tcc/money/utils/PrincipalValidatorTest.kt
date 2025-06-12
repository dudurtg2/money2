package com.tcc.money.utils

import com.tcc.money.utils.validator.PrincipalValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PrincipalValidatorTest {

    @Test
    fun `lista de movimentações não pode estar vazia`() {
        val listaValida = listOf("mov1", "mov2")
        val listaInvalida = emptyList<String>()

        assertTrue(PrincipalValidator.isListaMovimentacoesValida(listaValida))
        assertFalse(PrincipalValidator.isListaMovimentacoesValida(listaInvalida))
    }

    @Test
    fun `texto da entrada deve estar formatado corretamente`() {
        assertTrue(PrincipalValidator.isTextoEntradaFormatado("R$100,00"))
        assertFalse(PrincipalValidator.isTextoEntradaFormatado("100,00"))
    }

    @Test
    fun `texto do filtro deve conter palavras-chave`() {
        assertTrue(PrincipalValidator.isTextoFiltroFormatado("Última semana"))
        assertTrue(PrincipalValidator.isTextoFiltroFormatado("mês atual"))
        assertFalse(PrincipalValidator.isTextoFiltroFormatado("filtrar"))
    }

    @Test
    fun `valor de banco deve ser igual ou maior que zero`() {
        assertTrue(PrincipalValidator.isValorBancoValido(valor = 200.0f))
        assertTrue(PrincipalValidator.isValorBancoValido(valor = 0.0f))
        assertFalse(PrincipalValidator.isValorBancoValido(valor = -1.0f))
    }

    @Test
    fun `validar todos os dados da tela principal`() {
        val resultado = PrincipalValidator.validarTodos(
            nome = "Maria",
            saldo = 200.0f,
            entrada = 100.0f,
            filtro = "última semana",
            listaMovimentacoes = listOf("mov1", "mov2"),
            textoEntrada = "R$100,00",
            textoFiltro = "semana passada",
            valorBanco = 100.0f
        )

        assertTrue(resultado)
    }
}
