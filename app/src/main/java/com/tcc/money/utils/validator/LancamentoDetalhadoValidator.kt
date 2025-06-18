package com.tcc.money.utils.validator

object LancamentoDetalhadoValidator {

    fun isDestinoSelecionado(destino: Int?): Boolean {
        return destino != null
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
        destino: Int?,
        categoria: Int?,
        descricao: String?,
        parcelado: Boolean,
        qtdParcelas: Int?,
        valor: Double
    ): Boolean {
        return isDestinoSelecionado(destino) &&
                isCategoriaSelecionada(categoria) &&
                isDescricaoValida(descricao) &&
                isParcelamentoValido(parcelado, qtdParcelas) &&
                isValorValido(valor)
    }
}
