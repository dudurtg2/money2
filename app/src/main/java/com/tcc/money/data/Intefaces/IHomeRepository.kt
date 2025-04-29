package com.tcc.money.data.Intefaces

import com.tcc.money.data.models.HomeModelEntity

interface IHomeRepository {
    fun save(homeModelEntity: HomeModelEntity): HomeModelEntity
}