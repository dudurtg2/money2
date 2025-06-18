package com.tcc.money.utils.validator

import org.junit.Assert.*
import org.junit.Test

class LancamentoDespesaValidatorTest {

    @Test
    fun `origem não selecionada deve ser inválido`() {
        assertFalse(LancamentoDespesaValidator.isOrigemSelecionada(null))
    }

    @Test
    fun `categoria não selecionada deve ser inválido`() {
        assertFalse(LancamentoDespesaValidator.isCategoriaSelecionada(null))
    }

    @Test
    fun `descricao vazia ou nula é válida`() {
        assertTrue(LancamentoDespesaValidator.isDescricaoValida(""))
        assertTrue(LancamentoDespesaValidator.isDescricaoValida(null))
    }

    @Test
    fun `descricao muito curta é inválida`() {
        assertFalse(LancamentoDespesaValidator.isDescricaoValida("a"))
    }

    @Test
    fun `descricao com 3 ou mais caracteres é válida`() {
        assertTrue(LancamentoDespesaValidator.isDescricaoValida("mercado"))
    }

    @Test
    fun `parcelamento inválido quando parcelado e sem quantidade`() {
        assertFalse(LancamentoDespesaValidator.isParcelamentoValido(true, null))
        assertFalse(LancamentoDespesaValidator.isParcelamentoValido(true, 0))
    }

    @Test
    fun `parcelamento válido quando informado corretamente`() {
        assertTrue(LancamentoDespesaValidator.isParcelamentoValido(true, 3))
    }

    @Test
    fun `valor inválido se zero ou negativo`() {
        assertFalse(LancamentoDespesaValidator.isValorValido(0.0))
        assertFalse(LancamentoDespesaValidator.isValorValido(-50.0))
    }

    @Test
    fun `valor válido se maior que zero`() {
        assertTrue(LancamentoDespesaValidator.isValorValido(150.0))
    }

    @Test
    fun `validação completa válida`() {
        val result = LancamentoDespesaValidator.validarTodos(
            origem = 1,
            categoria = 2,
            descricao = "Mercado",
            parcelado = true,
            qtdParcelas = 5,
            valor = 200.0
        )
        assertTrue(result)
    }

    @Test
    fun `validação completa inválida se falta categoria`() {
        val result = LancamentoDespesaValidator.validarTodos(
            origem = 1,
            categoria = null,
            descricao = "Mercado",
            parcelado = false,
            qtdParcelas = null,
            valor = 200.0
        )
        assertFalse(result)
    }
}
