package com.tcc.money.network.api

import com.tcc.money.data.Intefaces.ICoinsRepository
import com.tcc.money.data.Intefaces.IMovementsRepository
import com.tcc.money.data.models.Coins
import com.tcc.money.data.models.Goals
import com.tcc.money.data.models.Movements
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface GoalsApi {
    @GET("api/goals/findAll")
      fun findAll(): Response<ResponseBody>

    @GET("api/goals/find/{uuid}")
      fun findByUUID(@Path("uuid") uuid: UUID,): Response<ResponseBody>

    @POST("api/goals/save")
      fun save(@Body goals: Goals): Response<ResponseBody>
}
