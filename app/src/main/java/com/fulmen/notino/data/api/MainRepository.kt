package com.fulmen.notino.data.api

import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getNotinoData() = apiHelper.getNotinoData()
}