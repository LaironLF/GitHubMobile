package com.laironlf.githubmobile.presentation.fragments.authorization

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laironlf.githubmobile.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val token: MutableLiveData<String> = MutableLiveData("")

    private val _state: MutableLiveData<State> = MutableLiveData(State.Idle)
    val state: LiveData<State> = _state

    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: Flow<Action> = _actions.receiveAsFlow()

    fun onSignButtonPressed() = viewModelScope.launch {
        handleSignIn()
    }

    private suspend fun handleSignIn() = try {
        _state.postValue(State.Loading)
        appRepository.signIn(token.value!!)
        _actions.send(Action.RouteToMain)
    } catch (e: Exception) {
        when (e) {
            is HttpException -> _state.postValue(State.InvalidInput(MSG_INVALID_TOKEN))
            is IllegalArgumentException -> _state.postValue(State.InvalidInput(MSG_INVALID_TOKEN))
            is UnknownHostException -> _actions.send(Action.ShowError(MSG_CONNECTION_ERROR))
            else -> {
                _actions.send(Action.ShowError(e.message.toString()))
                _state.postValue(State.Idle)
            }
        }
    }


    companion object {
        const val MSG_CONNECTION_ERROR = "Connection error"
        const val MSG_INVALID_TOKEN = "invalid token"
    }

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }
}