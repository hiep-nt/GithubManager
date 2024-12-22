package com.kai.githubmanager.domain.usecases

import com.kai.githubmanager.domain.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userName: String) = userRepository.getUserDetail(userName)
}