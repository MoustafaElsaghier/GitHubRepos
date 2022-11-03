package com.moustafa.githubrepos.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "repos")
@Parcelize
data class RepositoryModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(defaultValue = "")
    val name: String,
    val watchers_count: Int,
    @ColumnInfo(defaultValue = "")
    val description: String,
) : Parcelable