package com.moustafa.githubrepos.ui.features.repos.user_uepos_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.moustafa.githubrepos.data.db.entities.RepositoryModel
import com.moustafa.githubrepos.data.repo.GetRepositoriesRxRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UserRepositoriesViewModel(private val repository: GetRepositoriesRxRepository) : ViewModel() {
    private val disposable = CompositeDisposable()

    init {
        getRepositories()
    }

    val repositoryModel = MutableLiveData<PagingData<RepositoryModel>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getRepositories() {
        disposable.add(
            repository
                .getRepositories()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    repositoryModel.postValue(it)
                })

    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}