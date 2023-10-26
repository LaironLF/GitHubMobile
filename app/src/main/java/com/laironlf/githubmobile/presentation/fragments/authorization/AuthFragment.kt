package com.laironlf.githubmobile.presentation.fragments.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.laironlf.githubmobile.R
import com.laironlf.githubmobile.databinding.FragmentAuthBinding
import com.laironlf.githubmobile.presentation.fragments.authorization.AuthViewModel.State
import com.laironlf.githubmobile.presentation.utils.bindTextTwoWay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeToActions()
        setupAuthUIElements()
        subscribeToState()
    }

    private fun subscribeToState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.textInputLayout.error = if (state is State.InvalidInput) state.reason else null
            

        }
    }

    private fun setupAuthUIElements() {
        with(binding) {
            editTextToken.bindTextTwoWay(
                liveData = viewModel.token,
                lifecycleOwner = viewLifecycleOwner
            )
            ButtonSignIn.setOnClickListener { viewModel.onSignButtonPressed() }
        }
    }

    private fun subscribeToActions() {
        lifecycleScope.launch {
            viewModel.actions.collect { handleAction(it) }
        }
    }

    private fun handleAction(action: AuthViewModel.Action) {
        when (action) {
            is AuthViewModel.Action.ShowError -> Toast.makeText(
                context,
                action.message,
                Toast.LENGTH_SHORT
            ).show()

            is AuthViewModel.Action.RouteToMain -> routeToMain()
        }
    }

    private fun routeToMain() {
        binding.root.findNavController().navigate(R.id.action_authorizationFragment_to_home_nav)
    }

}