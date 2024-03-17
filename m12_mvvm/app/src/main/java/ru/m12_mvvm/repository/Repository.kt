package ru.m12_mvvm.repository

import kotlinx.coroutines.delay

class Repository {
    suspend fun find(searchText: String): String {
        delay(5000)
        return "Nothing was found for the request $searchText"
    }
}