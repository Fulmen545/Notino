package com.fulmen.notino.data.api

import com.fulmen.notino.data.model.NotinoResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getNotinoData(): Response<NotinoResponse>
}