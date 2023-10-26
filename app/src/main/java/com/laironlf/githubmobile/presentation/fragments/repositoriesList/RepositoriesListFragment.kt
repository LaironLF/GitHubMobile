package com.laironlf.githubmobile.presentation.fragments.repositoriesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.laironlf.githubmobile.R
import com.laironlf.githubmobile.databinding.FragmentRepositoriesListBinding
import com.laironlf.githubmobile.presentation.adapters.RepoListAdapter
import com.laironlf.githubmobile.presentation.fragments.repositoriesList.RepositoriesListViewModel.State
import com.laironlf.githubmobile.presentation.fragments.repositoryInfo.DetailInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesListFragment : Fragment() {

    private val viewModel: RepositoriesListViewModel by viewModels()
    private lateinit var binding: FragmentRepositoriesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindToViewModel()
    }

    private fun bindToViewModel() {
        val adapter = RepoListAdapter(::onItemClick)
        binding.connectionErrorView.buttonRetry.setOnClickListener { viewModel.onRetryPressed() }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRetryPressed()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        setupRecyclerRepos(adapter)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.visibility = if (state is State.Loading) View.VISIBLE else View.GONE
            binding.recyclerRepos.visibility =
                if (state is State.Loaded) View.VISIBLE else View.GONE
            binding.connectionErrorView.root.visibility =
                if (state is State.Error && state.error == "connection error") View.VISIBLE else View.GONE

            adapter.items = if (state is State.Loaded) state.repos else emptyList()
        }
    }

    private fun setupRecyclerRepos(adapter: RepoListAdapter) {
        binding.recyclerRepos.layoutManager = LinearLayoutManager(context)
        binding.recyclerRepos.adapter = adapter
    }

    private fun onItemClick(repoId: String, repoName: String) {
        findNavController().navigate(
            R.id.action_repositoriesFragment_to_detailInfoFragment,
            DetailInfoFragment.createArguments(repoId, repoName)
        )
    }
}