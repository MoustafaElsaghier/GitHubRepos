package com.moustafa.githubrepos.ui.user_uepos_list.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.moustafa.githubrepos.ui.user_uepos_list.viewholder.LoadingStateViewHolder

class LoadingStateAdapter : LoadStateAdapter<LoadingStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        return LoadingStateViewHolder.create(parent)
    }
}