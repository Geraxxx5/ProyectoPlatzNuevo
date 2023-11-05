package com.example.proyectoplatzapplication.ui.ExpenseReport

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ReporteGastos(navController: NavController){
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(70.dp))
        Divider()
        Text(
            text = "REPORTES DE GASTOS",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "GASTOS SEMANALES",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Divider()
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Column(Modifier.padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Semana\nAntepasada", textAlign = TextAlign.Center)
                CircularProgressIndicator(progress = 0.5f, color = Color.Green)
                Text(text = "50%", textAlign = TextAlign.Center)
            }
            Column(Modifier.padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Semana\nPasada", textAlign = TextAlign.Center)
                CircularProgressIndicator(progress = 0.7f, color = Color.Green)
                Text(text = "70%", textAlign = TextAlign.Center)
            }
            Column(Modifier.padding(horizontal = 20.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Esta Semana", textAlign = TextAlign.Center)
                CircularProgressIndicator(progress = 0.3f, color = Color.Green)
                Text(text = "30%", textAlign = TextAlign.Center)
            }
        }
        Text(text = "Distribucion de gastos", Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        Divider()
        val barValues = listOf(50f, 80f, 30f, 70f, 90f)
        val barWidth = 30f
        val barColor = Color.Blue
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
            listOf("Gastos de Alimentacion",1300.50),
            listOf("Gastos Uso Personal",720.25),
            listOf("Deudas",420.83),
        )
        booking.forEachIndexed { index, p ->
            Text(text = "${p[0]}:      ${p[1]}", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Divider()
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun ReportesPreview(){
    val navController = rememberNavController()
    ReporteGastos(navController = navController)
}