package ru.user_profile.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.user_profile.api.ApiService
import ru.user_profile.repository.UserRepository
import ru.user_profile.ui.viewmodel.UserProfileViewModel

@Suppress("UNCHECKED_CAST")
class UserProfileViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(UserRepository()) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}