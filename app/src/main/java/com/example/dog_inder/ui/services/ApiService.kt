package com.example.dog_inder.ui.services

import com.example.dog_inder.utils.model.Card
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET()
    suspend fun getImg(): Call<List<Card>>
}
