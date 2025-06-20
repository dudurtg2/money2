import com.tcc.money.utils.validator.MetasValidator


import org.junit.Assert.*
import org.junit.Test

class MetasValidatorTest {

    @Test
    fun `nome válido retorna true`() {
        assertTrue(MetasValidator.validarNome("Meta válida"))
    }

    @Test
    fun `nome vazio retorna false`() {
        assertFalse(MetasValidator.validarNome(""))
    }

    @Test
    fun `valor maior que zero retorna true`() {
        assertTrue(MetasValidator.validarValor(100.0))
    }

    @Test
    fun `valor zero retorna false`() {
        assertFalse(MetasValidator.validarValor(0.0))
    }

    @Test
    fun `valor negativo retorna false`() {
        assertFalse(MetasValidator.validarValor(-50.0))
    }

    @Test
    fun `data válida retorna true`() {
        assertTrue(MetasValidator.validarData("20/12/2025"))
    }

    @Test
    fun `data vazia retorna false`() {
        assertFalse(MetasValidator.validarData(""))
    }

    @Test
    fun `descricao válida retorna true`() {
        assertTrue(MetasValidator.validarDescricao("Meta para comprar um carro"))
    }

    @Test
    fun `descricao vazia retorna false`() {
        assertFalse(MetasValidator.validarDescricao(""))
    }

    @Test
    fun `todos os campos válidos retorna true`() {
        assertTrue(
            MetasValidator.validarTodos(
                nome = "Meta",
                valor = 500.0,
                data = "20/12/2025",
                descricao = "Comprar um carro"
            )
        )
    }

    @Test
    fun `algum campo inválido retorna false`() {
        assertFalse(
            MetasValidator.validarTodos(
                nome = "",
                valor = 0.0,
                data = "",
                descricao = ""
            )
        )
    }
}
