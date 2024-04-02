package ru.bored.data

import ru.bored.data.api.RetrofitBuilder
import javax.inject.Inject

class UsefulActivityDataSource @Inject constructor() {

    suspend fun loadUsefulActivity(): UsefulActivityDto {
         return RetrofitBuilder.apiService.getRandomUsefulActivity()
    }
}