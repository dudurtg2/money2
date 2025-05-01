package com.tcc.money.utils.enums

enum class TypeAccount(val value: Int) {
    NORMAL(0),
    PREMIUM(1);


    fun getValue(): Int {
        return value
    }
}