package com.kai.githubmanager.ui.screens.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kai.githubmanager.domain.usecases.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
): ViewModel() {

    private val _userDetailState = MutableStateFlow<UserDetailUIState>(UserDetailUIState.Loading)
    val userDetailState = _userDetailState.asStateFlow()

    fun getUserDetail(userName: String) = viewModelScope.launch {
        runCatching {
            val detail = getUserDetailUseCase.invoke(userName)
            _userDetailState.value = UserDetailUIState.Success(detail)
        }.getOrElse {
            _userDetailState.value = UserDetailUIState.Error(it.message ?: "Unknown error occurred")
        }

    }
}