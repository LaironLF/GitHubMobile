package com.laironlf.githubmobile.presentation.fragments.repositoriesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laironlf.githubmobile.domain.entities.Repo
import com.laironlf.githubmobile.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RepositoriesListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State> = _state

    init {
        viewModelScope.launch { fetchRepos() }
    }

    fun onRetryPressed() = viewModelScope.launch { fetchRepos() }

    private suspend fun fetchRepos() = try {
        _state.postValue(State.Loading)
        with(appRepository.getRepositories()) {
            if (this.isEmpty()) _state.postValue(State.Empty)
            else _state.postValue(State.Loaded(this))
        }
    } catch (e: Exception) {
        when (e) {
            is HttpException -> _state.postValue(State.Error(e.code().toString()))
            is UnknownHostException -> _state.postValue(State.Error("connection error"))
            else -> _state.postValue(State.Error(e.toString()))
        }
    }

    sealed interface State {
        data object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        data object Empty : State
    }

}