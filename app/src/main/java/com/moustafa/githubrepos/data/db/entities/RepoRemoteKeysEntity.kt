package com.moustafa.githubrepos.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "repository_remote_keys")
data class RepoRemoteKeysEntity(
    @PrimaryKey val keyId: Int,
    val prevKey: Int?,
    val nextKey: Int?
) : Parcelable
