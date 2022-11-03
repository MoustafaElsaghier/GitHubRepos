package com.moustafa.githubrepos.ui.user_uepos_list

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import com.moustafa.githubrepos.R
import com.moustafa.githubrepos.databinding.ActivityMainBinding
import com.moustafa.githubrepos.ui.user_uepos_list.adapter.LoadingStateAdapter
import com.moustafa.githubrepos.ui.user_uepos_list.adapter.RepositoriesRxAdapter
import com.moustafa.githubrepos.ui.user_uepos_list.viewmodel.UserRepositoriesViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter = RepositoriesRxAdapter()
    private val userRepositoriesViewModel: UserRepositoriesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initReposRecycler()
        initLiveData()
    }

    private fun initReposRecycler() {
        binding.reposRecycler.adapter = adapter
        // set bottom footer for showing loader
        binding.reposRecycler.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )
        adapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.retry) { _, _ ->
                        // to retry load data again
                        adapter.retry()
                    }
                    .show()
            }
        }

    }

    private fun initLiveData() {
        // hide progressbar once first page loaded
        binding.progressBar.isVisible = false
        // observe on changes from the api
        userRepositoriesViewModel.repositoryModel.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}