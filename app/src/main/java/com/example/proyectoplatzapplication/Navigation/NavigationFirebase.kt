package com.example.proyectoplatzapplication.Navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class UserUi(val nombre: String?, val email: String?, val loading:Boolean = true)
class NavigationFirebase: ViewModel()  {
    private val auth : FirebaseAuth = Firebase.auth
    var UserUiState by mutableStateOf(UserUi("",""))
        private set

    fun singOut(){
        auth.signOut()
    }

    fun getShowDetails(){
        UserUiState = UserUi("","",false)
        viewModelScope.launch {
            val userId = auth.currentUser!!.uid
            val usersRef = FirebaseFirestore.getInstance().collection("users")
            val snapshot = usersRef.whereEqualTo("user_id", userId).get().await()
            val document = snapshot.documents[0]
            val displayName = document.getString("display_name")
            val email = document.getString("email")
            UserUiState = UserUi(displayName, email)
        }
    }
}