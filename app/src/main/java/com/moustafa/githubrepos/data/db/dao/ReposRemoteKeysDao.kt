package com.moustafa.githubrepos.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moustafa.githubrepos.data.db.entities.RepoRemoteKeysEntity

@Dao
interface ReposRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RepoRemoteKeysEntity>)

    @Query("SELECT * FROM repository_remote_keys WHERE keyId = :keyId")
    fun remoteKeysByRepoId(keyId: Int): RepoRemoteKeysEntity?

    @Query("DELETE FROM repository_remote_keys")
    fun clearRemoteKeys()

}