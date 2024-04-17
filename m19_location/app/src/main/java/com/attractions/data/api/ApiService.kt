package com.attractions.data.api

import com.attractions.data.api.dto.SightDto
import retrofit2.http.GET
import retrofit2.http.Query


private const val API_KEY = "5ae2e3f221c38a28845f05b61eed5fac4a5fcbe01aba8df28f5f84ce"

interface ApiService {
    @GET("radius")
    suspend fun findSights(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("apikey") apikey: String = API_KEY
        ): SightDto
}