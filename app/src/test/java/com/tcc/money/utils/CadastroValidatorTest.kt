package com.tcc.money.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

class CadastroValidatorTest {

    private fun gerarNomeAleatorio(): String {
        val nomes = listOf("Ana", "Carlos", "Beatriz", "Lucas", "Jéssica")
        return nomes.random()
    }

    private fun gerarCpfAleatorio(tamanho: Int): String {
        return (1..tamanho).joinToString("") { Random.nextInt(0, 9).toString() }
    }

    @Test
    fun `nome válido gerado aleatoriamente deve ser aceito`() {
        val nome = gerarNomeAleatorio()
        val resultado = CadastroValidator.isNomeValido(nome)
        assertTrue(resultado)
    }

    @Test
    fun `nome inválido vazio deve ser rejeitado`() {
        val resultado = CadastroValidator.isNomeValido("")
        assertFalse(resultado)
    }

    @Test
    fun `sobrenome válido gerado aleatoriamente deve ser aceito`() {
        val sobrenome = gerarNomeAleatorio() // Reutilizando função pra simular sobrenome
        val resultado = CadastroValidator.isSobrenomeValido(sobrenome)
        assertTrue(resultado)
    }

    @Test
    fun `sobrenome inválido vazio deve ser rejeitado`() {
        val resultado = CadastroValidator.isSobrenomeValido("")
        assertFalse(resultado)
    }

    @Test
    fun `cpf válido com 11 dígitos aleatórios deve ser aceito`() {
        val cpf = gerarCpfAleatorio(11)
        val resultado = CadastroValidator.isCpfValido(cpf)
        assertTrue(resultado)
    }

    @Test
    fun `cpf inválido com menos de 11 dígitos deve ser rejeitado`() {
        val cpf = gerarCpfAleatorio(5)
        val resultado = CadastroValidator.isCpfValido(cpf)
        assertFalse(resultado)
    }

    @Test
    fun `data de nascimento válida aleatória deve ser aceita`() {
        val data = "01/01/${Random.nextInt(1970, 2020)}"
        val resultado = CadastroValidator.isDataNascimentoValida(data)
        assertTrue(resultado)
    }

    @Test
    fun `data de nascimento vazia deve ser rejeitada`() {
        val resultado = CadastroValidator.isDataNascimentoValida("")
        assertFalse(resultado)
    }
}
