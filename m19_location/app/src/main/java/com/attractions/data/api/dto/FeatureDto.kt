package com.attractions.data.api.dto

import com.attractions.entity.Feature
import com.google.gson.annotations.SerializedName


data class FeatureDto(
    @SerializedName("type") override val type: String,
    @SerializedName("id") override val id: String,
    @SerializedName("geometry") override val geometry: PointDto,
    @SerializedName("properties") override val properties: PropertyDto,
): Feature
