package com.example.proyectoplatzapplication.ui.ExpenseReport

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectoplatzapplication.ui.registro.Gasto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun calculateTotalOf( index:String,gastos: SnapshotStateList<Gasto>): Int{
    var contTemp = 0
    for(i in gastos){
        if(i.categoria == index){
            contTemp += i.cantidadGastada.toInt()
        }
    }
    return contTemp
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReporteGastos(navController: NavController, viewModel: ReporteGastosViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val gastos = remember { mutableStateListOf<Gasto>() }
    var alimentacion by remember { mutableDoubleStateOf(0.0) }
    var usoPersonal by remember { mutableDoubleStateOf(0.0) }
    var deudas by remember { mutableDoubleStateOf(0.0) }
    var emergencias by remember { mutableDoubleStateOf(0.0) }
    var myMap by remember { mutableStateOf(mutableMapOf<String, Double>()) }
    var barValues by remember { mutableStateOf(listOf(50f, 80f, 30f, 70f, 90f)) }
    //val categorias = listOf("Comida", "Deudas", "Emergencia", "Uso Personal")
    LaunchedEffect(key1 = viewModel) {
        val values = viewModel.obtainExpenses()
        alimentacion = viewModel.getSumOfExpensesByCategory("Comida")
        usoPersonal = viewModel.getSumOfExpensesByCategory("Uso Personal")
        deudas = viewModel.getSumOfExpensesByCategory("Deudas")
        emergencias = viewModel.getSumOfExpensesByCategory("Emergencia")
        myMap = viewModel.getExpensePercentages()
        Log.d("MapaDevuelto", "Mapa: ${myMap["Esta semana"]}")
        barValues = viewModel.getAmountsOrderedByDate()
        withContext(Dispatchers.Main) {
            for (i in values) {
                Log.d("ListaDevolver", "Fecha: ${i.date}")
                gastos.add(
                    Gasto(
                        fecha = i.date!!,
                        categoria = i.category!!,
                        descripcion = i.description!!,
                        cantidadGastada = i.amount!!.toString()
                    )
                )
            }
        }
    }


    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(70.dp))
        Divider()
        Text(
            text = "REPORTE DE GASTOS", modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF12950E),
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp

            ))
        Text(
            text = "GASTOS SEMANALES",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Divider()
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Column(Modifier.padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Semana\nAntepasada", textAlign = TextAlign.Center)
                if (myMap.contains("Hace dos semanas")) {
                    CircularProgressIndicator(progress = myMap["Hace dos semanas"]!!.toFloat(), color = Color.Green)
                    Text(text = "${String.format("%.2f",myMap["Hace dos semanas"]!! * 100)}%", textAlign = TextAlign.Center)
                } else {
                    CircularProgressIndicator(progress = 0f, color = Color.Green)
                    Text(text = "0", textAlign = TextAlign.Center)
                }
            }
            Column(Modifier.padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Semana\nPasada", textAlign = TextAlign.Center)
                if(myMap.contains("Semana pasada")){
                    CircularProgressIndicator(progress = myMap["Semana pasada"]!!.toFloat(), color = Color.Green)
                    Text(text = "${String.format("%.2f",myMap["Semana pasada"]!! * 100)}%", textAlign = TextAlign.Center)
                }else{
                    CircularProgressIndicator(progress = 0f, color = Color.Green)
                    Text(text = "0", textAlign = TextAlign.Center)
                }

            }
            Column(Modifier.padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Esta Semana", textAlign = TextAlign.Center)
                if(myMap.contains("Esta semana")){
                    CircularProgressIndicator(progress = myMap["Esta semana"]!!.toFloat(), color = Color.Green)
                    Text(text = "${String.format("%.2f",myMap["Esta semana"]!! * 100)}%", textAlign = TextAlign.Center)
                }else{
                    CircularProgressIndicator(progress = 0f, color = Color.Green)
                    Text(text = "0", textAlign = TextAlign.Center)
                }
            }
        }
        Text(text = "Distribucion de gastos", Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        Divider()
        val barWidth = 30f
        val barColor = Color(0xFF12950E)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Gray),
                modifier = Modifier.padding(16.dp)
            ) {
                Canvas(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    val barHeight = 150f // Altura máxima de las barras
                    val barSpacing = 50f // Espacio entre las barras

                    barValues.forEachIndexed { index, value ->
                        val x = index * (barWidth + barSpacing)
                        val y = size.height - (value / 100) * barHeight
                        drawRect(color = barColor, topLeft = Offset(x, y), size = Size(barWidth, size.height - y))
                    }
                }
            }
        }
        Text(text = "Bookings", Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        Divider()
        val booking = listOf(
            listOf("Gastos de Alimentacion",alimentacion),
            listOf("Gastos Uso Personal",usoPersonal),
            listOf("Deudas",deudas),
            listOf("Emergencias",emergencias)
        )
        booking.forEachIndexed { index, p ->
            Text(text = "${p[0]}:      ${p[1]}", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Divider()
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun ReportesPreview(){
    val navController = rememberNavController()
    ReporteGastos(navController = navController)
}