package com.kai.githubmanager.domain.usecases

import androidx.paging.PagingData
import com.kai.githubmanager.domain.UserRepository
import com.kai.githubmanager.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(perPage: Int): Flow<PagingData<User>> {
        return userRepository.getPagedUsers(perPage)
    }
}