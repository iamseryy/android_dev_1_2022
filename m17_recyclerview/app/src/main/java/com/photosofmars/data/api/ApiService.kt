package ru.bored.data.api


import retrofit2.http.GET
import ru.bored.data.UsefulActivityDto


interface ApiService {
    @GET("activity/")
    suspend fun getRandomUsefulActivity() : UsefulActivityDto
}