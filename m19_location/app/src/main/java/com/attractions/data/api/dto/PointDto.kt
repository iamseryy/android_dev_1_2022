package com.attractions.data.api.dto

import com.attractions.entity.Point
import com.google.gson.annotations.SerializedName


data class PointDto(
    @SerializedName("coordinates") override val coordinates: List<Double>
): Point
