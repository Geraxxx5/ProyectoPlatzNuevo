package com.example.proyectoplatzapplication.ui.registro

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegistroGastosViewModel: ViewModel()  {
    private val auth : FirebaseAuth = Firebase.auth

    suspend fun obtainExpenses(): MutableList<Expense> = withContext(Dispatchers.IO){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        var toReturn: MutableList<Expense> = mutableListOf()

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            Log.d("Firestore", "${document.id} => ${document.data}")
            val gastosRef = document.reference.collection("gastos")

            val gastos = gastosRef.get().await()
            for (gastoDocument in gastos) {
                Log.d("Firestore", "${gastoDocument.id} => ${gastoDocument.data}")
                val gasto = gastoDocument.toObject(Expense::class.java)
                toReturn.add(gasto)
                // Ahora puedes acceder a las propiedades del gasto
                Log.d("Firestore", "Fecha: ${gasto.date}")
                Log.d("Firestore", "Monto: ${gasto.amount}")
                Log.d("Firestore", "Categoría: ${gasto.category}")
                Log.d("Firestore", "Categoría: ${gasto.description}")
            }
        }
        Log.d("ListaDevolver", "Fecha: ${toReturn.size}")
        toReturn

    }

    fun createNewExpense(expense: Expense){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")

        usersRef.whereEqualTo("user_id", userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                    val gastosRef = document.reference.collection("gastos")

                    gastosRef.add(expense)
                        .addOnSuccessListener {
                            Log.d("Finanzas Personales","Creado ${it.id}")
                        }.addOnFailureListener{
                            Log.d("Finanzas Personales","Ocurrio Error ${it}")
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Error getting documents: ", exception)
            }
    }
}