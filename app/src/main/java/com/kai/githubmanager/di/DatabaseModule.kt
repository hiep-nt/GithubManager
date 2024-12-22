package com.kai.githubmanager.di

import android.content.Context
import androidx.room.Room
import com.kai.githubmanager.data.localdb.dao.UserDao
import com.kai.githubmanager.data.localdb.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "UserDatabase"
        ).build()
    }

    @Provides
    fun provideUserDao(
        database: UserDatabase
    ): UserDao {
        return database.userDao()
    }
}