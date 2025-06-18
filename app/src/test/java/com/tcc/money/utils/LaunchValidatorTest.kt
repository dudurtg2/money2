package com.tcc.money.utils.validator

import org.junit.Assert.*
import org.junit.Test

class LaunchValidatorTest {

    @Test
    fun `valor vazio deve ser inválido`() {
        assertFalse(LaunchValidator.isValorValido(""))
    }

    @Test
    fun `valor zero deve ser inválido`() {
        assertFalse(LaunchValidator.isValorValido("0"))
    }

    @Test
    fun `valor negativo deve ser inválido`() {
        assertFalse(LaunchValidator.isValorValido("-100"))
    }

    @Test
    fun `valor com formato válido deve ser válido`() {
        assertTrue(LaunchValidator.isValorValido("100"))
        assertTrue(LaunchValidator.isValorValido("100,50"))
        assertTrue(LaunchValidator.isValorValido("100.75"))
    }

    @Test
    fun `valor não numérico deve ser inválido`() {
        assertFalse(LaunchValidator.isValorValido("abc"))
    }
}
