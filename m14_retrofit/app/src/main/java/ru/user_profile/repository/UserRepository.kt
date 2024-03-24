package ru.user_profile.repository

import kotlinx.coroutines.delay
import ru.user_profile.api.RetrofitBuilder
import ru.user_profile.exception.ApiServiceException
import ru.user_profile.model.Results


private const val REQUEST_FAILED = "The request failed"

class UserRepository() {
    suspend fun getRandomUser(): Results? {
        delay(2000)
        val response = RetrofitBuilder.apiService.getRandomUser()
        if(!response.isSuccessful) throw ApiServiceException(REQUEST_FAILED)
         return  response.body()
    }
}