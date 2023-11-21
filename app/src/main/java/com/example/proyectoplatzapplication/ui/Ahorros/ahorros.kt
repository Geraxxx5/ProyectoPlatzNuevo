package com.example.proyectoplatzapplication.ui.Ahorros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme

class AhorrosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPlatzApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "ahorros") {
                    composable("ahorros") { AhorrosScreen(navController) }
                    composable("nuevo_plan_ahorro") { NuevoPlanDeAhorroScreen() }
                    composable("plan_actual_ahorro") { PlanActualDeAhorroScreen() }
                    composable("modificar_plan_ahorro") { ModificarPlanDeAhorroScreen() }
                    composable("monto_planes_ahorro") { MontoPlanesAhorroScreen() }
                }
            }
        }
    }
}

@Composable
fun AhorrosScreen(navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ahorros",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF12950E),
                fontFamily = FontFamily.SansSerif,
                fontSize = 25.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFFD4FDAC))
                .fillMaxWidth()
                .height(80.dp)
                .clickable { navController.navigate("nuevo_plan_ahorro") }
        ) {
            Text(
                text = "Nuevo plan de ahorro",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFFD4FDAC))
                .fillMaxWidth()
                .height(80.dp)
                .clickable { navController.navigate("plan_actual_ahorro") }
        ) {
            Text(
                text = "Plan actual de ahorro",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFFD4FDAC))
                .fillMaxWidth()
                .height(80.dp)
                .clickable { navController.navigate("modificar_plan_ahorro") }
        ) {
            Text(
                text = "Modificar plan de ahorro",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .background(color = Color(0xFFD4FDAC))
                .fillMaxWidth()
                .height(100.dp)
                .clickable { navController.navigate("monto_planes_ahorro") }
        ) {
            Text(
                text = "Monto de tus planes de ahorro",
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp
            )
        }
        // ... resto del código de AhorrosScreen
    }
}

@Composable
fun NuevoPlanDeAhorroScreen() {
    Text("Nuevo Plan de Ahorro")
}

@Composable
fun PlanActualDeAhorroScreen() {
    Text("Plan Actual de Ahorro")
}

@Composable
fun ModificarPlanDeAhorroScreen() {
    Text("Modificar Plan de Ahorro")
}

@Composable
fun MontoPlanesAhorroScreen() {
    Text("Monto de tus Planes de Ahorro")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPlatzApplicationTheme {
        AhorrosScreen(rememberNavController())
    }
}
