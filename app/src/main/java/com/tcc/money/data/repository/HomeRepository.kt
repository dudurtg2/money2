package com.tcc.money.data.repository

import com.tcc.money.data.Intefaces.IHomeRepository
import com.tcc.money.data.models.HomeModelEntity

class HomeRepository: IHomeRepository {
    override fun save(homeModelEntity: HomeModelEntity): HomeModelEntity {
        return homeModelEntity
    }
}