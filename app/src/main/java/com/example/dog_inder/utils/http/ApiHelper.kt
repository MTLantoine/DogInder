package com.example.dog_inder.utils.http

import com.example.dog_inder.ui.services.ApiService
import retrofit2.http.GET

class ApiHelper(private val apiService: ApiService) {

    @GET(".")
    suspend fun getImg() = apiService.getImg()
}