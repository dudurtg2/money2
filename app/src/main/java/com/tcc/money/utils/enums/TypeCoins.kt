package com.tcc.money.utils.enums

enum class TypeCoins(val value: Int) {
    CRIPTO(0),
    BANK(1),
    INVESTMENTS(2),
    MONEY(3),
    PHYSICAL(4),
    OTHER(5);

    fun getValue(): Int {
        return value
    }
}