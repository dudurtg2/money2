package com.tcc.money.utils.validator

object LancamentoDespesaValidator {

    fun isOrigemSelecionada(origem: Int?): Boolean {
        return origem != null
    }

    fun isCategoriaSelecionada(categoria: Int?): Boolean {
        return categoria != null
    }

    fun isDescricaoValida(descricao: String?): Boolean {
        return descricao.isNullOrBlank() || descricao.length >= 3
    }

    fun isParcelamentoValido(parcelado: Boolean, qtdParcelas: Int?): Boolean {
        return !parcelado || (qtdParcelas != null && qtdParcelas > 0)
    }

    fun isValorValido(valor: Double): Boolean {
        return valor > 0.0
    }

    fun validarTodos(
        origem: Int?,
        categoria: Int?,
        descricao: String?,
        parcelado: Boolean,
        qtdParcelas: Int?,
        valor: Double
    ): Boolean {
        return isOrigemSelecionada(origem) &&
                isCategoriaSelecionada(categoria) &&
                isDescricaoValida(descricao) &&
                isParcelamentoValido(parcelado, qtdParcelas) &&
                isValorValido(valor)
    }
}
