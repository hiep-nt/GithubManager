package com.kai.githubmanager.data.network.models

import com.google.gson.annotations.SerializedName
import com.kai.githubmanager.domain.models.User

data class UserResponse(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("followers")
    val followers: Int? = null,
    @SerializedName("following")
    val following: Int? = null
) {
    fun toUser(): User {
        return User(
            userName = this.login ?: "",
            avatarUrl = this.avatarUrl ?: "",
            htmlUrl = this.htmlUrl ?: "",
            location = this.location ?: "",
            followers = this.followers ?: 0,
            following = this.following ?: 0
        )
    }
}