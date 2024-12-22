package com.kai.githubmanager.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kai.githubmanager.domain.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userName: String,
    val avatarUrl: String,
    val htmlUrl: String
)

fun UserEntity.toUser() = User(
    userName = this.userName,
    avatarUrl = this.avatarUrl,
    htmlUrl = this.htmlUrl
)

fun User.toEntity() = UserEntity(
    userName = this.userName,
    avatarUrl = this.avatarUrl,
    htmlUrl = this.htmlUrl
)