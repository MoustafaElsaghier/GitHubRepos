package com.moustafa.githubrepos.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(defaultValue = "")
    val name: String,
    @ColumnInfo(defaultValue = "")
    val full_name: String,
    val watchers_count: Int,
    @ColumnInfo(defaultValue = "")
    val description: String,
    @ColumnInfo(defaultValue = "")
    val language: String,
) : Serializable