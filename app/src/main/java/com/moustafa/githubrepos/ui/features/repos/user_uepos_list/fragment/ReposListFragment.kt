package com.moustafa.githubrepos.ui.features.repos.user_uepos_list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.moustafa.githubrepos.R
import com.moustafa.githubrepos.databinding.FragmentReposListBinding
import com.moustafa.githubrepos.ui.features.repos.user_uepos_list.adapter.LoadingStateAdapter
import com.moustafa.githubrepos.ui.features.repos.user_uepos_list.adapter.RepositoriesRxAdapter
import com.moustafa.githubrepos.ui.features.repos.user_uepos_list.viewmodel.UserRepositoriesViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ReposListFragment : Fragment() {
    private lateinit var binding: FragmentReposListBinding
    private var adapter = RepositoriesRxAdapter()
    private val userRepositoriesViewModel: UserRepositoriesViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReposListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReposRecycler()
        initLiveData()
    }

    private fun initReposRecycler() {
        adapter.onRepoClickListener = {
            findNavController().navigate(
                ReposListFragmentDirections.actionReposListFragmentToRepoDetailsFragment(
                    it
                )
            )
        }
        binding.reposRecycler.adapter = adapter
        // set bottom footer for showing loader
        binding.reposRecycler.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error

                errorState?.let {
                    AlertDialog.Builder(requireContext())
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

    }

    private fun initLiveData() {
        // hide progressbar once first page loaded
        binding.progressBar.isVisible = false
        // observe on changes from the api
        userRepositoriesViewModel.repositoryModel.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

}