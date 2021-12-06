package com.fulmen.notino.data.api

import com.fulmen.notino.data.model.NotinoResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("db")
    suspend fun getNotinoData(): Response<NotinoResponse>
}