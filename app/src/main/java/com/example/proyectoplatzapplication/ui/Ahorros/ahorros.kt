package com.example.proyectoplatzapplication.ui.Ahorros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoplatzapplication.ui.Contactos.Greeting
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme


class ahorros : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPlatzApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AhorrosScreen()
                }
            }
        }
    }
}
@Composable
fun AhorrosScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Text(
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
        ) {
            Text(
                text = "Nuevo plan de ahorro",
                modifier = Modifier.align(Alignment.Center) ,
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
        ) {
            Text(
                text = "Plan actual de ahorro",
                modifier = Modifier.align(Alignment.Center) ,
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
        ) {
            Text(
                text = "Modificar plan de ahorro",
                modifier = Modifier.align(Alignment.Center) ,
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
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
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
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Text(
                text = "Ahorros Actuales",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,fontFamily = FontFamily.Serif,fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )

            androidx.compose.material3.Text(
                text = "Total Invertido",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,fontFamily = FontFamily.Serif,fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )

        }
        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPlatzApplicationTheme {
        AhorrosScreen()
    }
}
