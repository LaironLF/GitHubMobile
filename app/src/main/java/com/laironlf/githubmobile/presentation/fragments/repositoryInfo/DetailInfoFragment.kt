package com.laironlf.githubmobile.presentation.fragments.repositoryInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.laironlf.githubmobile.databinding.FragmentDetailInfoBinding
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.presentation.fragments.repositoryInfo.RepositoryInfoViewModel.ReadmeState
import com.laironlf.githubmobile.presentation.fragments.repositoryInfo.RepositoryInfoViewModel.State
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon

@AndroidEntryPoint
class DetailInfoFragment : Fragment() {

    private val viewModel: RepositoryInfoViewModel by viewModels()
    private val repoId: String get() = requireNotNull(requireArguments().getString(REPO_ID_KEY))
    private val repoName: String get() = requireNotNull(requireArguments().getString(REPO_NAME_KEY))

    private lateinit var binding: FragmentDetailInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailInfoBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.title = repoName
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindToViewModel()
        viewModel.onDetailInfoFragmentCreated(repoId)
    }

    private fun bindToViewModel() {
        binding.connectionErrorView.buttonRetry.setOnClickListener { viewModel.onRetryPressed(repoId) }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.visibility = if (state is State.Loading) View.VISIBLE else View.GONE
            binding.connectionErrorView.root.visibility =
                if (state is State.Error) View.VISIBLE else View.GONE
            if (state is State.Loaded) {
                binding.repoDetailInfo.visibility = View.VISIBLE
                inflateWithData(repoDetails = state.githubRepo)
                handleReadme(state)
            } else {
                binding.repoDetailInfo.visibility = View.GONE
            }
        }
    }

    private fun inflateWithData(repoDetails: RepoDetails) {
        with(binding) {
            textViewStarsCount.text = repoDetails.starsCount.toString()
            textViewForksCount.text = repoDetails.forksCount.toString()
            textViewWatchersCount.text = repoDetails.watchersCount.toString()
            repoUrl.text = repoDetails.htmlUrl
        }
    }

    private fun handleReadme(state: State.Loaded) {
        when (state.readmeState) {
            is ReadmeState.Loaded -> fillMarkdown(binding.readmeText, state.readmeState.markdown)
            is ReadmeState.Empty -> binding.readmeText.text = NO_README_MESSAGE
            else -> binding.readmeText.text = ""
        }

        binding.readmeProgressBar.visibility =
            if (state.readmeState is ReadmeState.Loading) View.VISIBLE else View.GONE
        binding.readmeConnectionError.root.visibility =
            if (state.readmeState is ReadmeState.Error) View.VISIBLE else View.GONE
    }

    private fun fillMarkdown(textView: TextView, markdown: String) =
        Markwon.create(requireContext()).setMarkdown(textView, markdown)


    companion object {

        private const val NO_README_MESSAGE = "No README.md"
        private const val REPO_ID_KEY = "repoIdKey"
        private const val REPO_NAME_KEY = "repoTitleKey"
        fun createArguments(repoIdKey: String, repoNameKey: String): Bundle {
            return bundleOf(REPO_ID_KEY to repoIdKey, REPO_NAME_KEY to repoNameKey)
        }
    }
}