package com.moustafa.githubrepos.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.moustafa.githubrepos.data.db.RepositoriesRoomDB
import com.moustafa.githubrepos.data.db.entities.RepositoryModel
import com.moustafa.githubrepos.data.remote.RepositoriesRemoteMediator
import io.reactivex.Flowable

class GetRepositoriesRxRepository(
    private val database: RepositoriesRoomDB,
    private val remoteMediator: RepositoriesRemoteMediator
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getRepositories(): Flowable<PagingData<RepositoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true,
                maxSize = 20,
                prefetchDistance = 3,
                initialLoadSize = 15
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.getRepositoriesDao().pagingSource() }
        ).flowable
    }
}