package com.tcc.money.utils.enums

enum class TypeAccount(val value: Int) {
    NORMAL(0),
    PREMIUM(1);

    @JvmName("toIntValue")
    fun getValue(): Int = value
}
