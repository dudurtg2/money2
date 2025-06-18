package com.tcc.money.utils.validator

import org.junit.Assert.*
import org.junit.Test

class LancamentoDetalhadoValidatorTest {

    @Test
    fun `destino não selecionado deve ser inválido`() {
        assertFalse(LancamentoDetalhadoValidator.isDestinoSelecionado(null))
    }

    @Test
    fun `categoria não selecionada deve ser inválida`() {
        assertFalse(LancamentoDetalhadoValidator.isCategoriaSelecionada(null))
    }

    @Test
    fun `descricao vazia ou nula é válida`() {
        assertTrue(LancamentoDetalhadoValidator.isDescricaoValida(""))
        assertTrue(LancamentoDetalhadoValidator.isDescricaoValida(null))
    }

    @Test
    fun `descricao muito curta é inválida`() {
        assertFalse(LancamentoDetalhadoValidator.isDescricaoValida("a"))
    }

    @Test
    fun `descricao com 3 ou mais caracteres é válida`() {
        assertTrue(LancamentoDetalhadoValidator.isDescricaoValida("Venda"))
    }

    @Test
    fun `parcelamento inválido quando parcelado e sem quantidade`() {
        assertFalse(LancamentoDetalhadoValidator.isParcelamentoValido(true, null))
        assertFalse(LancamentoDetalhadoValidator.isParcelamentoValido(true, 0))
    }

    @Test
    fun `parcelamento válido quando informado corretamente`() {
        assertTrue(LancamentoDetalhadoValidator.isParcelamentoValido(true, 3))
    }

    @Test
    fun `valor inválido se zero ou negativo`() {
        assertFalse(LancamentoDetalhadoValidator.isValorValido(0.0))
        assertFalse(LancamentoDetalhadoValidator.isValorValido(-10.0))
    }

    @Test
    fun `valor válido se maior que zero`() {
        assertTrue(LancamentoDetalhadoValidator.isValorValido(150.0))
    }

    @Test
    fun `validação completa válida`() {
        val result = LancamentoDetalhadoValidator.validarTodos(
            destino = 1,
            categoria = 2,
            descricao = "Venda de produto",
            parcelado = true,
            qtdParcelas = 5,
            valor = 500.0
        )
        assertTrue(result)
    }

    @Test
    fun `validação completa inválida se falta destino`() {
        val result = LancamentoDetalhadoValidator.validarTodos(
            destino = null,
            categoria = 2,
            descricao = "Venda",
            parcelado = false,
            qtdParcelas = null,
            valor = 200.0
        )
        assertFalse(result)
    }
}
