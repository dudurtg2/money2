package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Movements
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface MovementsApi {
    @GET("api/movements/findAll")
      fun findAll(): Response<ResponseBody>

    @GET("api/movements/find/{uuid}")
      fun findByUUID(@Path("uuid") uuid: UUID,): Response<ResponseBody>

    @POST("api/movements/save")
      fun save(@Body movements: Movements): Response<ResponseBody>
}