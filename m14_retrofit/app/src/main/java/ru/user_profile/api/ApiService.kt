package ru.user_profile.api

import retrofit2.Response
import retrofit2.http.GET
import ru.user_profile.model.Results


interface ApiService {
    @GET("/api/")
    suspend fun getRandomUser() : Response<Results>
}