package com.example.proyectoplatzapplication.ui.Ahorros

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoplatzapplication.ui.registro.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AhorrosViewModel: ViewModel()  {
    private val auth : FirebaseAuth = Firebase.auth

     fun updateSavings(objective:String, goal:String, newCurrent:String){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        viewModelScope.launch{
            val documents = usersRef.whereEqualTo("user_id", userId).get().await()

            for (document in documents) {
                val savingsRef = document.reference.collection("ahorros")
                val savings = savingsRef.whereEqualTo("objective", objective).whereEqualTo("goal", goal).get().await()

                for (savingsDocument in savings) {
                    val savingsToUpdate = savingsDocument.reference
                    savingsToUpdate.update("current", newCurrent).await()
                }
            }
        }
    }
    suspend fun obtainSavings(): MutableList<Savings> = withContext(Dispatchers.IO){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        var toReturn: MutableList<Savings> = mutableListOf()

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            Log.d("Firestore", "${document.id} => ${document.data}")
            val gastosRef = document.reference.collection("ahorros")

            val gastos = gastosRef.get().await()
            for (gastoDocument in gastos) {
                Log.d("Firestore", "${gastoDocument.id} => ${gastoDocument.data}")
                val ahorro = gastoDocument.toObject(Savings::class.java)
                toReturn.add(ahorro)
            }
        }
        Log.d("ListaDevolver", "Fecha: ${toReturn.size}")
        toReturn
    }
    fun createNewSaving(savings: Savings){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")

        usersRef.whereEqualTo("user_id", userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                    val gastosRef = document.reference.collection("ahorros")

                    gastosRef.add(savings)
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