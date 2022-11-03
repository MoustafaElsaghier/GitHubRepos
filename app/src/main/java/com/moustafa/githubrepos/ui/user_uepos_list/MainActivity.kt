package com.moustafa.githubrepos.ui.user_uepos_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.moustafa.githubrepos.R
import com.moustafa.githubrepos.databinding.ActivityMainBinding
import com.moustafa.githubrepos.ui.user_uepos_list.adapter.RepositoriesRxAdapter
import com.moustafa.githubrepos.ui.user_uepos_list.viewmodel.UserRepositoriesViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var adapter = RepositoriesRxAdapter()
    private val userRepositoriesViewModel: UserRepositoriesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initReposRecycler()
        initLiveData()
    }

    private fun initReposRecycler() {
        binding.reposRecycler.adapter = adapter
        binding.reposRecycler
    }

    private fun initLiveData() {
        userRepositoriesViewModel.repositoryModel.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}