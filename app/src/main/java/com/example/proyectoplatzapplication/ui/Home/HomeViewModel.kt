package com.example.proyectoplatzapplication.ui.Home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoplatzapplication.Navigation.UserUi
import com.example.proyectoplatzapplication.ui.registro.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//val alimentos: Double, val usoPersonal: Double, val deudas: Double, val emergencias: Double
data class HomeUi(val values:List<Double>, val loading: Boolean = true)
class HomeViewModel: ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth
    var HomeUiState by mutableStateOf(HomeUi(emptyList()))
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDatatoShow(){
        HomeUiState = HomeUi(emptyList(),false)
        //val categorias = listOf("Comida", "Deudas", "Emergencia", "Uso Personal")
        viewModelScope.launch {
            val alimentos = getSumOfExpensesByCategoryToday("Comida")
            val deudas = getSumOfExpensesByCategoryToday("Deudas")
            val emergencias = getSumOfExpensesByCategoryToday("Emergencia")
            val usoPersonal = getSumOfExpensesByCategoryToday("Uso Personal")
            HomeUiState = HomeUi(listOf(alimentos,usoPersonal,deudas,emergencias))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getSumOfExpensesByCategoryToday(category: String): Double {
        val userId = auth.currentUser!!.uid
        val usersRef = FirebaseFirestore.getInstance().collection("users")
        var totalAmount = 0.0

        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val documents = usersRef.whereEqualTo("user_id", userId).get().await()
        for (document in documents) {
            val gastosRef = document.reference.collection("gastos")

            // Filtra los gastos por categoría y fecha
            val gastos = gastosRef.whereEqualTo("category", category).whereEqualTo("date", today).get().await()
            for (gastoDocument in gastos) {
                val gasto = gastoDocument.toObject(Expense::class.java)
                totalAmount += gasto.amount!!
            }
        }

        return totalAmount
    }
}