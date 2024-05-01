package com.attractions.domain

import com.attractions.data.SightRepository
import com.attractions.entity.Sight
import javax.inject.Inject

class SightUseCase @Inject constructor(
    private val repository: SightRepository
) {
    suspend fun findSight(
        lat: Double,
        lon: Double,
        radius: Int,
        limit: Int
    ): Sight {
        return repository.findSight(lat, lon,  radius, limit)
    }
}