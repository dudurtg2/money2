package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.models.Coins
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoinsApi: ICoinsRepository {
    @GET("api/coins/findAll")
    override suspend fun findAll(): List<Coins>

    @GET("api/coins/find/{id}")
    override suspend fun findById(@Path("id") id: Long): Coins

    @POST("api/coins/save")
    override suspend fun save(@Body coins: Coins): Coins
}