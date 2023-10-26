package com.laironlf.githubmobile.presentation.fragments.repositoryInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laironlf.githubmobile.domain.entities.RepoDetails
import com.laironlf.githubmobile.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State> = _state

    fun onDetailInfoFragmentCreated(repoId: String) = viewModelScope.launch {
        fetchRepoDetail(repoId)
    }

    fun onRetryPressed(repoId: String) = viewModelScope.launch {
        fetchRepoDetail(repoId)
    }

    private suspend fun fetchRepoDetail(id: String) = try {
        _state.postValue(State.Loading)
        val repoDetails: RepoDetails = appRepository.getRepository(id)
        _state.postValue(State.Loaded(repoDetails, ReadmeState.Loading))
        _state.postValue(State.Loaded(repoDetails, fetchRepoReadme(repoDetails)))

    } catch (e: Exception) {
        when (e) {
            is HttpException -> _state.postValue(State.Error(e.code().toString()))
            is UnknownHostException -> _state.postValue(State.Error("connection error"))
            else -> _state.postValue(State.Error(e.toString()))
        }
    }

    private suspend fun fetchRepoReadme(repoDetails: RepoDetails): ReadmeState = try {
        ReadmeState.Loaded(appRepository.getRepositoryReadme(repoDetails))
    } catch (e: Exception) {
        when (e) {
            is HttpException -> if (e.code() == 404) ReadmeState.Empty else ReadmeState.Error(e.toString())
            is UnknownHostException -> ReadmeState.Error("connection error")
            else -> ReadmeState.Error(e.toString())
        }
    }

    sealed interface State {
        data object Loading : State
        data class Error(val error: String) : State
        data class Loaded(
            val githubRepo: RepoDetails,
            val readmeState: ReadmeState
        ) : State
    }

    sealed interface ReadmeState {
        data object Loading : ReadmeState
        data object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }

    // TODO:
}