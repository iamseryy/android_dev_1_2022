package ru.user_profile.repository

import ru.user_profile.api.ApiService
import ru.user_profile.api.RetrofitBuilder

class UserRepository() {
    suspend fun getRandomUser() = RetrofitBuilder.apiService.getRandomUser().body()
}