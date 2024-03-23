package ru.user_profile.repository

import ru.user_profile.api.ApiService

class UserRepository (private val apiService: ApiService) {
    suspend fun getRandomUser() = apiService.getRandomUser().body()
}