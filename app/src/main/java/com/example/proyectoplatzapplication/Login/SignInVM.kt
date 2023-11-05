package com.example.proyectoplatzapplication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: ARepository
) : ViewModel() {

    val _signInState = Channel<SignIn>()
    val signInState = _signInState.receiveAsFlow()


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signInState.send(SignIn(isSuccess = "Sign In Success "))
                }
                is Resource.Loading -> {
                    _signInState.send(SignIn(isLoading = true))
                }
                is Resource.Error -> {

                    _signInState.send(SignIn(isError = result.message))
                }
            }

        }
    }

}