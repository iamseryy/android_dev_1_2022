package ru.user_profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import ru.user_profile.repository.UserRepository

class UserProfileViewModel(private val repository: UserRepository)  : ViewModel() {
    fun getRandomUser() {

    }
}