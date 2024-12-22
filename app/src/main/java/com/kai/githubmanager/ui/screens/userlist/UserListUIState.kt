package com.kai.githubmanager.ui.screens.userlist

import androidx.paging.PagingData
import com.kai.githubmanager.domain.models.User
import kotlinx.coroutines.flow.Flow

sealed class UserListUIState {
    object Loading : UserListUIState()
    data class Success(val data: Flow<PagingData<User>>) : UserListUIState()
    data class Error(val message: String) : UserListUIState()
}