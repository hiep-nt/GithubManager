package com.kai.githubmanager.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kai.githubmanager.data.localdb.dao.UserDao
import com.kai.githubmanager.data.localdb.entity.toEntity
import com.kai.githubmanager.data.localdb.entity.toUser
import com.kai.githubmanager.data.network.service.ApiService
import com.kai.githubmanager.domain.models.User

class UserPagingSource(
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val perPage: Int
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val since = params.key ?: 0

            if (since == 0) {
                val localUsers = userDao.getUsers().map { it.toUser() }
                if (localUsers.isNotEmpty()) {
                    return LoadResult.Page(
                        data = localUsers,
                        prevKey = null,
                        nextKey = localUsers.size
                    )
                }
            }

            val remoteUsers = apiService.getUsers(since, perPage).map { it.toUser() }

            if (since == 0) {
                userDao.insertUsers(remoteUsers.map { it.toEntity() })
            }

            return LoadResult.Page(
                data = remoteUsers,
                prevKey = if (since == 0) null else since - perPage,
                nextKey = if (remoteUsers.isEmpty()) null else since + remoteUsers.size
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(perPage)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(perPage)
        }
    }
}
