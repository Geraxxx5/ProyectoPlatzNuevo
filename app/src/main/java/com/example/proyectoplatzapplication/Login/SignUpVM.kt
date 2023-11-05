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
class SignUpVM @Inject constructor(
    private val repository : ARepository
) : ViewModel(){

    val _signUpState = Channel<SignUp>()
    val signUpState = _signUpState.receiveAsFlow()

    fun registerUser(email:String, password:String ) = viewModelScope.launch {
        repository.loginUser(email, password).collect{result ->
            when(result ){
                is Resource.Success ->{
                    _signUpState.send(SignUp(isSuccess = "Sing Up Exitoso! " ))
                }
                is  Resource.Loading ->{
                    _signUpState.send(SignUp(isLoading = true))
                }
                is  Resource.Error ->{
                    _signUpState.send(SignUp(isError = result.message ))
                }


            }
        }
    }
}