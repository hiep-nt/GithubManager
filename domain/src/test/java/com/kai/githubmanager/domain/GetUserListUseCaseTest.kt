package com.kai.githubmanager.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.kai.githubmanager.domain.models.User
import com.kai.githubmanager.domain.usecases.GetUserListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetUserListUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUserListUseCase: GetUserListUseCase

    @Before
    fun setUp() {
        userRepository = mockk()
        getUserListUseCase = GetUserListUseCase(userRepository)
    }

    @Test
    fun `When calling get users use case successful, it returns success`() {
        val expectedPagingData: Flow<PagingData<User>> = flow {
            emit(PagingData.from(MockData.userList))
        }

        coEvery { userRepository.getPagedUsers(any()) } returns expectedPagingData

        val result = getUserListUseCase.invoke(20)

        assert(result == expectedPagingData)
    }

    @Test
    fun `invoke returns empty PagingData when repository returns empty flow`() = runTest {
        coEvery { userRepository.getPagedUsers(20) } returns emptyFlow()

        val resultFlow = getUserListUseCase(20)

        resultFlow.collect { pagingData ->
            val actualList = pagingData.collectData()
            assertTrue(actualList.isEmpty())
        }
    }

    @Test
    fun `When calling get users use case failed, it returns error`() = runTest {
        val expected = IOException()
        coEvery { userRepository.getPagedUsers(any()) } returns flow { throw expected }

        try {
            getUserListUseCase(20).collectLatest {  }
        } catch (exception: Exception) {
            assertTrue(exception is IOException)
        }
    }

    suspend fun <T : Any> PagingData<T>.collectData(): List<T> {
        val items = mutableListOf<T>()
        this.map { items.add(it) }
        return items
    }
}