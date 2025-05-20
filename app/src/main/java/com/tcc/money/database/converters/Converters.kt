package com.tcc.money.database.converters

import androidx.room.TypeConverter
import com.tcc.money.utils.enums.TypeAccount
import com.tcc.money.utils.enums.TypeCoins
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? =
        date?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }

    @TypeConverter
    fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun fromTypeCoins(type: TypeCoins): String = type.name

    @TypeConverter
    fun toTypeCoins(value: String): TypeCoins = TypeCoins.valueOf(value)

    @TypeConverter
    fun toTypeAccount(value: String): TypeAccount = TypeAccount.valueOf(value)

    @TypeConverter
    fun fromTypeAccount(type: TypeAccount): String = type.name


}