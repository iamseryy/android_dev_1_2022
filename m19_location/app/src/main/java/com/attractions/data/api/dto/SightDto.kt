package com.attractions.data.api.dto


import com.attractions.entity.Sight
import com.google.gson.annotations.SerializedName

data class SightDto (
    @SerializedName("type") override val type: String,
    @SerializedName("features") override val features: List<FeatureDto>
): Sight
