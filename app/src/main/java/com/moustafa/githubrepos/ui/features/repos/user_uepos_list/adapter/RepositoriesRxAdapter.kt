package com.moustafa.githubrepos.ui.features.repos.user_uepos_list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.moustafa.githubrepos.data.db.entities.RepositoryModel
import com.moustafa.githubrepos.ui.features.repos.user_uepos_list.viewholder.ReposViewHolder

class RepositoriesRxAdapter : PagingDataAdapter<RepositoryModel, ReposViewHolder>(
    COMPARATOR
) {

    lateinit var onRepoClickListener: ((item: RepositoryModel) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        getItem(position)?.let { repoModel ->
            holder.bind(repoModel)
            holder.itemView.setOnClickListener {
                onRepoClickListener.invoke(repoModel)
            }
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RepositoryModel>() {
            override fun areItemsTheSame(
                oldItem: RepositoryModel,
                newItem: RepositoryModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepositoryModel,
                newItem: RepositoryModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
