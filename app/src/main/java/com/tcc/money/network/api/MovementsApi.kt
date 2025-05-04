package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovementsApi: IMovementsRepository {
    @GET("api/movements/findAll")
    override suspend fun findAll(): List<Movements>

    @GET("api/movements/find/{id}")
    override suspend fun findById(@Path("id") id: Long): Movements

    @POST("api/movements/save")
    override suspend fun save(@Body movements: Movements): Movements
}