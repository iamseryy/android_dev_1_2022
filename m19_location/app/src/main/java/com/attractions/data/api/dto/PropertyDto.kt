package com.attractions.data.api.dto

import com.attractions.entity.Property
import com.google.gson.annotations.SerializedName

data class PropertyDto(
    @SerializedName("xid") override val xid: String,
    @SerializedName("name") override val name: String,
    @SerializedName("dist") override val dist: Float,
    @SerializedName("rate") override val rate: Int,
    @SerializedName("kinds") override val kinds: String,
): Property
