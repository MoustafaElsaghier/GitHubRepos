package com.moustafa.githubrepos.data.remote

import com.moustafa.githubrepos.data.models.RepositoryModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPIService {

    @GET("users/JakeWharton/repos")
    fun getRequest(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Single<List<RepositoryModel>>

}