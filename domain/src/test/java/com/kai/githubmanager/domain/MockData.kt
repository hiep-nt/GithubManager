package com.kai.githubmanager.domain

import com.kai.githubmanager.domain.models.User

object MockData {

    val userList = listOf(
        User(
            userName = "username 1",
            avatarUrl = "avatarUrl 1",
            htmlUrl = "htmlUrl 1",
            location = "location 1",
            followers = 1,
            following = 1,
        ),
        User(
            userName = "username 2",
            avatarUrl = "avatarUrl 2",
            htmlUrl = "htmlUrl 2",
            location = "location 2",
            followers = 2,
            following = 2,
        ),
    )
}