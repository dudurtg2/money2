package com.tcc.money.database.exception

class SyncErrorException @JvmOverloads constructor(
    message: String = "Sincronização falhou.",
    val statusCode: Int = 400,
    cause: Throwable? = null
) : RuntimeException(message, cause)