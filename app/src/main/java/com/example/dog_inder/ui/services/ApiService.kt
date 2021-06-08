package com.example.dog_inder.ui.services

import com.example.dog_inder.utils.model.ImageData
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getImg(): ImageData
}
