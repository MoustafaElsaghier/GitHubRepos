package com.moustafa.githubrepos.ui.features.repos.user_uepos_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.moustafa.githubrepos.R
import com.moustafa.githubrepos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavController()
    }

    private fun initNavController() {
        navController = binding.navHostRepositories.getFragment<NavHostFragment>().navController

        NavigationUI.setupWithNavController(
            binding.toolbarRepos,
            navController
        )

    }


}