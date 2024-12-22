package com.kai.githubmanager

import androidx.paging.PagingData
import com.kai.githubmanager.Constants.ITEM_PER_PAGE
import com.kai.githubmanager.domain.models.User
import com.kai.githubmanager.domain.usecases.GetUserListUseCase
import com.kai.githubmanager.ui.screens.userlist.UserListUIState
import com.kai.githubmanager.ui.screens.userlist.UserListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class UserListViewModelTest {

    private lateinit var getUserListUseCase: GetUserListUseCase

    private lateinit var viewModel: UserListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUserListUseCase = mockk()
        viewModel = UserListViewModel(getUserListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchUserList updates uiState with Success when use case succeeds`() {
        runTest {
            val mockUserList = List(5) { User(userName = "User $it") }
            coEvery { getUserListUseCase(ITEM_PER_PAGE) } returns flow<PagingData<User>> {
                emit(PagingData.from(mockUserList))
            }

            viewModel.fetchUserList()
            delay(1000)

            assert(
                viewModel.uiState.value is UserListUIState.Success
            )
        }
    }

    @Test
    fun `fetchUserList updates uiState with Error when use case fails`() = runTest {
        val errorMessage = "Network error"
        coEvery { getUserListUseCase(ITEM_PER_PAGE) } throws Exception(errorMessage)

        viewModel.fetchUserList()
        delay(1000)

        assertEquals(
            UserListUIState.Error(errorMessage),
            viewModel.uiState.value
        )
    }
}

