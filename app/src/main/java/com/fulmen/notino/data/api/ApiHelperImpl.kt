package com.fulmen.notino.data.api

import com.fulmen.notino.data.model.NotinoResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {
    override suspend fun getNotinoData(): Response<NotinoResponse> = apiService.getNotinoData()
}