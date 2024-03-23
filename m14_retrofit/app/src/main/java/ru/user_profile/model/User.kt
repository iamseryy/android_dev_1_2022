package ru.user_profile.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("gender") val gender: Gender,
    @SerializedName("name") val name: Name,
//    val location: Location,
//    val email: String,
//    val login: Login,
//    val dob: ProfileDate,
//    val registered: ProfileDate,
//    val phone: String,
//    val cell: String,
//    val id: Id,
//    val picture: Picture,
//    val nat: String
)
