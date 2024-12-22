package com.kai.githubmanager.domain

import androidx.paging.PagingData
import com.kai.githubmanager.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {


    /**
     * Get a flow of paged users
     * @param perPage The number of users to get per page
     * @return A flow of paged users
     */
    fun getPagedUsers(perPage: Int): Flow<PagingData<User>>

    /**
     * Get a user's details from the API
     * @param username The username of the user
     * @return The user's details
     */
    suspend fun getUserDetail(username: String): User
}