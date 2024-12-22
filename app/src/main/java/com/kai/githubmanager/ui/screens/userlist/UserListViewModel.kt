package com.kai.githubmanager.ui.screens.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.githubmanager.Constants.ITEM_PER_PAGE
import com.kai.githubmanager.domain.usecases.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserListUIState>(UserListUIState.Loading)
    val uiState: StateFlow<UserListUIState> get() = _uiState

    init {
        fetchUserList()
    }

    fun fetchUserList() {
        viewModelScope.launch {
            try {
                _uiState.value = UserListUIState.Success(getUserListUseCase(ITEM_PER_PAGE))
            } catch (e: Exception) {
                _uiState.value = UserListUIState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}