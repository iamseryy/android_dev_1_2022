package com.photosofmars.data.api


import com.photosofmars.data.dto.PhotoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


private const val API_KEY = "sSXJoDfXZafUksOnyLh0yoGVy7hjFFvBi7gOFCDo"

interface ApiService {
    @GET("/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotoByDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ) : List<PhotoDto>
}