package com.kai.githubmanager

import com.kai.githubmanager.domain.models.User
import com.kai.githubmanager.domain.usecases.GetUserDetailUseCase
import com.kai.githubmanager.ui.screens.userdetail.UserDetailUIState
import com.kai.githubmanager.ui.screens.userdetail.UserDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserDetailViewModelTest {

    private lateinit var userDetailViewModel: UserDetailViewModel

    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserDetailUseCase = mockk()
        userDetailViewModel = UserDetailViewModel(getUserDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserDetail updates userDetailState with Success when use case succeeds`() {
        runTest {

            val mockUser = User(userName = "Test User")
            val mockUserDetailState = UserDetailUIState.Success(mockUser)

            coEvery { getUserDetailUseCase("User") } returns mockUser

            userDetailViewModel.getUserDetail("User")
            delay(1000)

            assertEquals(
                userDetailViewModel.userDetailState.value , mockUserDetailState
            )
        }
    }

    @Test
    fun `getUserDetail updates userDetailState with Error when use case fails`() {
        runTest {

            val mockUserDetailState = UserDetailUIState.Error("Unknown error occurred")

            coEvery { getUserDetailUseCase("User") } throws Exception()

            userDetailViewModel.getUserDetail("User")
            delay(1000)

            assertEquals(
                userDetailViewModel.userDetailState.value , mockUserDetailState
            )
        }
    }
}