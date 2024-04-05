package com.photosofmars.data.api


import com.photosofmars.data.dto.PhotosDto
import retrofit2.http.GET
import retrofit2.http.Query


private const val API_KEY = "sSXJoDfXZafUksOnyLh0yoGVy7hjFFvBi7gOFCDo"

interface ApiService {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotoByDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): PhotosDto
}