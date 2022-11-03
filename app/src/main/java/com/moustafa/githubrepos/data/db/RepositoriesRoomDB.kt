package com.moustafa.githubrepos.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moustafa.githubrepos.data.db.dao.ReposRemoteKeysDao
import com.moustafa.githubrepos.data.db.dao.RepositoriesDao
import com.moustafa.githubrepos.data.db.entities.RepoRemoteKeysEntity
import com.moustafa.githubrepos.data.db.entities.RepositoryModel

@Database(
    entities = [RepositoryModel::class, RepoRemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverters::class)
abstract class RepositoriesRoomDB : RoomDatabase() {

    abstract fun getRepositoriesDao(): RepositoriesDao
    abstract fun getRepositoriesKeyDao(): ReposRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: RepositoriesRoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): RepositoriesRoomDB {
            return Room.databaseBuilder(
                context.applicationContext,
                RepositoriesRoomDB::class.java,
                "github_repos.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}