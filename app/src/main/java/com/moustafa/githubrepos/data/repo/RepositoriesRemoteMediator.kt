package com.moustafa.githubrepos.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.moustafa.githubrepos.data.api.GithubAPIService
import com.moustafa.githubrepos.data.db.RepositoriesRoomDB
import com.moustafa.githubrepos.data.db.entities.RepoRemoteKeysEntity
import com.moustafa.githubrepos.data.db.entities.RepositoryModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val database: RepositoriesRoomDB,
    private val networkService: GithubAPIService
) : RxRemoteMediator<Int, RepositoryModel>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, RepositoryModel>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        remoteKeys?.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        remoteKeys?.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    networkService.getReposRequest(
                        page = page
                    )
                        .map {
                            insertToDb(page, loadType, it)
                        }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                        .onErrorReturn {
                            MediatorResult.Error(it)
                        }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }

    }

    private fun insertToDb(
        page: Int,
        loadType: LoadType,
        data: List<RepositoryModel>
    ): List<RepositoryModel> {

        database.runInTransaction {
            if (loadType == LoadType.REFRESH) {
                database.getRepositoriesKeyDao().clearRemoteKeys()
                database.getRepositoriesDao().clearAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.isEmpty()) null else page + 1
            val keys = data.map {
                RepoRemoteKeysEntity(keyId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            database.getRepositoriesKeyDao().insertAll(keys)
            database.getRepositoriesDao().insertAll(data)
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, RepositoryModel>): RepoRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.getRepositoriesKeyDao().remoteKeysByRepoId(repo.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, RepositoryModel>): RepoRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
            database.getRepositoriesKeyDao().remoteKeysByRepoId(repo.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RepositoryModel>): RepoRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getRepositoriesKeyDao().remoteKeysByRepoId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }

}