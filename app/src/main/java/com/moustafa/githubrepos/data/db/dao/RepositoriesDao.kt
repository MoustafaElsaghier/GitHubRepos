package com.moustafa.githubrepos.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moustafa.githubrepos.data.models.RepositoryModel

@Dao
interface RepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<RepositoryModel>)

    @Query("SELECT * FROM repos ")
    fun pagingSource(): PagingSource<Int, RepositoryModel>

    @Query("DELETE FROM repos")
    suspend fun clearAll()
}
