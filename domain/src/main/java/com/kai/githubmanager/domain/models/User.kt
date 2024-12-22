package com.kai.githubmanager.domain.models

data class User(
    val userName: String = "",
    val avatarUrl: String = "",
    val htmlUrl: String = "",
    val location: String = "",
    val followers: Int = 0,
    val following: Int = 0,
)