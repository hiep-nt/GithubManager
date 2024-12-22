package com.kai.githubmanager.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kai.githubmanager.data.localdb.dao.UserDao
import com.kai.githubmanager.data.network.service.ApiService
import com.kai.githubmanager.domain.UserRepository
import com.kai.githubmanager.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
): UserRepository {

    override fun getPagedUsers(perPage: Int): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = perPage),
            pagingSourceFactory = { UserPagingSource(userDao, apiService, perPage) }
        ).flow
    }

    override suspend fun getUserDetail(username: String): User {
        return apiService.getUserDetail(username).toUser()
    }

}