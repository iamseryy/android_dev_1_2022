package com.attractions.data

import com.attractions.data.api.ApiService
import com.attractions.data.api.dto.SightDto
import javax.inject.Inject

class SightRepository @Inject constructor(
    private val apiService : ApiService
){
    suspend fun findSight(
        lat: Double,
        lon: Double,
        radius: Int,
        limit: Int
    ): SightDto {
       return apiService.findSights(lat, lon, radius, limit)
    }
}