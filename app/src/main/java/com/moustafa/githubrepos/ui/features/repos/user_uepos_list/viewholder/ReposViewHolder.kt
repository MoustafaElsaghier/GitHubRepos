package com.moustafa.githubrepos.ui.features.repos.user_uepos_list.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moustafa.githubrepos.R
import com.moustafa.githubrepos.data.db.entities.RepositoryModel
import com.moustafa.githubrepos.databinding.RepoItemLayoutBinding

class ReposViewHolder(private val binding: RepoItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(repositoryModel: RepositoryModel) {
        binding.repoModel = repositoryModel
        binding.repoNumber.text = "# $bindingAdapterPosition"
    }

    companion object {
        fun create(parent: ViewGroup): ReposViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_item_layout, parent, false)

            val binding = RepoItemLayoutBinding.bind(view)

            return ReposViewHolder(
                binding
            )
        }
    }
}
