package com.kai.githubmanager.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.kai.githubmanager.data.localdb.dao.UserDao
import com.kai.githubmanager.data.network.models.UserResponse
import com.kai.githubmanager.data.network.service.ApiService
import com.kai.githubmanager.data.repository.UserPagingSource
import com.kai.githubmanager.data.repository.UserRepositoryImpl
import com.kai.githubmanager.domain.models.User
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepositoryImpl
    private val userDao: UserDao = mockk()
    private val apiService: ApiService = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = UserRepositoryImpl(userDao, apiService)
        userRepository = UserRepositoryImpl(userDao, apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPagedUsers returns paged users successfully`() {
        runTest {
            val perPage = 1
            val mockApiResponse = listOf(UserResponse(login = "Mock User"))
            val expectedUsers = listOf(User(userName = "Mock User"))

            coEvery { apiService.getUsers(any(), any()) } returns mockApiResponse
            coEvery { userDao.getUsers() } returns emptyList()
            coEvery { userDao.insertUsers(any()) } just Runs

            val expectedResult = PagingSource.LoadResult.Page(
                data = expectedUsers,
                prevKey = null,
                nextKey = 1
            )

            val result = UserPagingSource(userDao, apiService, perPage).load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )

            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun `getUserDetail returns user details successfully`() {
        runTest {
            val username = "testUser"
            val mockApiResponse = UserResponse(login = "Mock User")
            val expectedUser = User(userName = "Mock User")

            coEvery { apiService.getUserDetail(username) } returns mockApiResponse

            val result = userRepository.getUserDetail(username)

            assertEquals(expectedUser.userName, result.userName)
            coVerify { apiService.getUserDetail(username) }
        }
    }
}

