package com.moustafa.githubrepos.ui.features.repos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.moustafa.githubrepos.R
import com.moustafa.githubrepos.databinding.ActivityMainBinding

class RepositoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavController()
    }

    private fun initNavController() {
        val appBarConfig =
            AppBarConfiguration.Builder(setOf(R.id.reposListFragment, R.id.repoDetailsFragment))
                .build()
        navController = binding.navHostRepositories.getFragment<NavHostFragment>().navController

        NavigationUI.setupWithNavController(binding.toolbarRepos, navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.repoDetailsFragment,
                -> {
                    binding.toolbarRepos.setNavigationIcon(R.drawable.ic_toolbar_back)
                    binding.toolbarRepos.setNavigationOnClickListener {
                        navController.popBackStack()
                    }

                }
                else -> {
                    binding.toolbarRepos.navigationIcon = null
                }
            }

        }
    }


}