package ru.user_profile.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: Name,
    @SerializedName("location") val location: Location,
    @SerializedName("email") val email: String,
    @SerializedName("login") val login: Login,
    @SerializedName("dob") val dob: ProfileDate,
    @SerializedName("registered") val registered: ProfileDate,
    @SerializedName("phone") val phone: String,
    @SerializedName("cell") val cell: String,
    @SerializedName("id") val id: Id,
    @SerializedName("picture") val picture: Picture,
    @SerializedName("nat") val nat: String
)
