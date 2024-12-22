package com.kai.githubmanager.domain

import com.kai.githubmanager.domain.usecases.GetUserDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetUserDetailUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        getUserDetailUseCase = GetUserDetailUseCase(userRepository)
    }

    @Test
    fun `When calling get user detail use case successful, it returns success`() = runTest {
        val expectedUser = MockData.userList.first()

        coEvery { userRepository.getUserDetail(any()) } returns expectedUser

        val result = getUserDetailUseCase.invoke(expectedUser.userName)

        assert(result == expectedUser)
    }

    @Test
    fun `When calling get user detail use case failed, it returns error`() = runTest {
        val expected = IOException()
        coEvery { userRepository.getUserDetail(any()) } throws expected

        try {
            getUserDetailUseCase.invoke("userName")
        } catch (e: Exception) {
            assert(e == expected)
        }
    }
}