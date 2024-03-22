package ru.user_profile.model

data class User(
    val gender: Gender,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: ProfileDate,
    val registered: ProfileDate,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String
)
