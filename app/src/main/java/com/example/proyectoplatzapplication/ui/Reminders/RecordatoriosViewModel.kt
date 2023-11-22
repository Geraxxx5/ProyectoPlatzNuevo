package com.example.proyectoplatzapplication.ui.Reminders

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proyectoplatzapplication.ui.registro.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RecordatoriosViewModel: ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth

    fun createNewReminder(reminders: Reminders){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")

        usersRef.whereEqualTo("user_id", userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                    val gastosRef = document.reference.collection("recordatorios")

                    gastosRef.add(reminders)
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

    suspend fun obtainReminders(): MutableList<Reminders> = withContext(Dispatchers.IO){
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        var toReturn: MutableList<Reminders> = mutableListOf()

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            Log.d("Firestore", "${document.id} => ${document.data}")
            val gastosRef = document.reference.collection("recordatorios")

            val gastos = gastosRef.get().await()
            for (gastoDocument in gastos) {
                Log.d("Firestore", "${gastoDocument.id} => ${gastoDocument.data}")
                val gasto = gastoDocument.toObject(Reminders::class.java)
                toReturn.add(gasto)
                // Ahora puedes acceder a las propiedades del gasto
            }
        }
        Log.d("ListaDevolver", "Fecha: ${toReturn.size}")
        toReturn

    }

    fun deleteReminder(reminderTitle: String, date: String) {
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")

        usersRef.whereEqualTo("user_id", userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val remindersRef = document.reference.collection("recordatorios")

                    remindersRef.whereEqualTo("reminderTitle", reminderTitle).whereEqualTo("date", date).get()
                        .addOnSuccessListener { reminderDocuments ->
                            for (reminderDocument in reminderDocuments) {
                                reminderDocument.reference.delete()
                                    .addOnSuccessListener {
                                        Log.d("Finanzas Personales", "Recordatorio eliminado con éxito")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.d("Finanzas Personales", "Error al eliminar el recordatorio", e)
                                    }
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.d("Finanzas Personales", "Error al obtener los recordatorios", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Error getting documents: ", exception)
            }
    }
}