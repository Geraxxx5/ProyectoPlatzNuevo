package com.example.proyectoplatzapplication.Login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoplatzapplication.Navigation.MainActivity
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)


    fun signInWithEmailAndPassword(email: String, password: String, MainActivity:() -> Unit)
    = viewModelScope.launch {
        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    task ->
                    if (task.isSuccessful){
                        Log.d("Finanzas Personales","SignIn exitoso!")
                        MainActivity()
                    }
                    else{
                        Log.d("Finanzas Personales","SignIn : ${task.result.toString()}")
                    }
                }
        }
        catch (ex:Exception){
            Log.d("Finanzas Personales","SignIn: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email : String,
        password: String,
        MainActivity:() -> Unit
    ){
        if(_loading.value==false ){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    task ->
                    if (task.isSuccessful){
                        val displayName =
                            task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                       MainActivity()
                    }
                    else{
                        Log.d("Finanzas Personales", "CreateUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false

                }
        }

    }

    private fun createUser(displayName: String?) {
val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any> ()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()
     FirebaseFirestore.getInstance().collection("users")
         .add(user)
         .addOnSuccessListener {
             Log.d("Finanzas Personales","Creado ${it.id}")
         }.addOnFailureListener{
             Log.d("Finanzas Personales","Ocurrio Error ${it}")
         }
    }
}
