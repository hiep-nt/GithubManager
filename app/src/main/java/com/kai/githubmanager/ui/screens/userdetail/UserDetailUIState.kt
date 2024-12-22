package com.kai.githubmanager.ui.screens.userdetail

import com.kai.githubmanager.domain.models.User

sealed class UserDetailUIState {

    object Loading : UserDetailUIState()
    data class Success(val data: User) : UserDetailUIState()
    data class Error(val message: String) : UserDetailUIState()
}