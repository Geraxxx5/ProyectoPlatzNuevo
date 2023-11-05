package com.example.proyectoplatzapplication.Login

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow


interface ARepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
}