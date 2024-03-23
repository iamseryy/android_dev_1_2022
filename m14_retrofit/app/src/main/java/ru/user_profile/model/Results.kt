package ru.user_profile.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("results") val results: List<User>
)
