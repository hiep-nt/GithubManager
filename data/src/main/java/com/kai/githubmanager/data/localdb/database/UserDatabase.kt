package com.kai.githubmanager.data.localdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kai.githubmanager.data.localdb.dao.UserDao
import com.kai.githubmanager.data.localdb.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
}