package com.example.proyectoplatzapplication.ui.ExpenseReport

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.proyectoplatzapplication.ui.registro.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReporteGastosViewModel: ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth

    suspend fun getSumOfExpensesByCategory(category: String): Double {
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        var totalAmount = 0.0

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            val gastosRef = document.reference.collection("gastos")

            val gastos = gastosRef.whereEqualTo("category", category).get().await()
            for (gastoDocument in gastos) {
                val gasto = gastoDocument.toObject(Expense::class.java)
                totalAmount += gasto.amount!!
            }
        }

        return totalAmount
    }

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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getExpensePercentages(): MutableMap<String, Double> {
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Obtén las fechas de inicio y fin de cada semana
        val now = LocalDate.now()
        val thisWeekStart = now.with(DayOfWeek.MONDAY)
        val lastWeekStart = thisWeekStart.minusWeeks(1)
        val twoWeeksAgoStart = lastWeekStart.minusWeeks(1)

        // Inicializa las sumas
        var totalAmount = 0.0
        var thisWeekAmount = 0.0
        var lastWeekAmount = 0.0
        var twoWeeksAgoAmount = 0.0

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            val gastosRef = document.reference.collection("gastos")

            val gastos = gastosRef.get().await()
            for (gastoDocument in gastos) {
                val gasto = gastoDocument.toObject(Expense::class.java)
                val date = LocalDate.parse(gasto.date, formatter)
                val amount = gasto.amount

                totalAmount += amount!!
                when {
                    date.isAfter(thisWeekStart) -> thisWeekAmount += amount!!
                    date.isAfter(lastWeekStart) -> lastWeekAmount += amount!!
                    date.isAfter(twoWeeksAgoStart) -> twoWeeksAgoAmount += amount!!
                }
            }
        }

        val thisWeekPercentage = if (totalAmount > 0) thisWeekAmount / totalAmount * 1.0 else 0.0
        val lastWeekPercentage = if (totalAmount > 0) lastWeekAmount / totalAmount * 1.0 else 0.0
        val twoWeeksAgoPercentage = if (totalAmount > 0) twoWeeksAgoAmount / totalAmount * 1.0 else 0.0

        Log.d("Resultados", "esta Semana => ${thisWeekPercentage}")
        Log.d("Resultados", "semana pasada => ${lastWeekPercentage}")
        Log.d("Resultados", "esta 2 semanas => ${twoWeeksAgoPercentage}")

        // Devuelve los porcentajes en un mapa
        return mutableMapOf(
            "Esta semana" to thisWeekPercentage,
            "Semana pasada" to lastWeekPercentage,
            "Hace dos semanas" to twoWeeksAgoPercentage
        )
    }

    suspend fun getAmountsOrderedByDate(): List<Float> {
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        val amounts = mutableListOf<Float>()

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            val gastosRef = document.reference.collection("gastos")

            val gastos = gastosRef.orderBy("date").get().await()
            for (gastoDocument in gastos) {
                val gasto = gastoDocument.toObject(Expense::class.java)
                Log.d("Ordenado", "${gasto.date} => ${gasto.amount}")
                amounts.add((gasto.amount!! / 10).toFloat())
            }
        }

        return amounts
    }


}