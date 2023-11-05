package com.example.proyectoplatzapplication.ui.Reminders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Reminders(navController: NavController){
    val daysOfWeek = listOf("D", "L", "M", "X", "J", "V", "S")
    var checkedState by remember { mutableStateOf(false) }
    Column {
        Spacer(modifier = Modifier.height(70.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Gray),
                modifier = Modifier.padding(16.dp)
            ){
                Column {
                    Row {
                        daysOfWeek.forEach { day ->
                            Text(
                                text = day,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    repeat(5) {
                        Row {
                            repeat(7) { day ->
                                Text(
                                    text = ((it * 7 + day) + 1).toString(),
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
        Text(text = "Hoy",Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        Divider()
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
            )
            Text(text = "Pagar Netflix            ")
            Text(text = "100")
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
            )
            Text(text = "Pagar Universidad            ")
            Text(text = "6000")
        }
        Text(text = "Proxima Semana",Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        Divider()
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
            )
            Text(text = "Pagar Spotify            ")
            Text(text = "100")
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
            )
            Text(text = "Pagar F1            ")
            Text(text = "75.25")
        }
        Text(text = "Proxima Mes",Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        Divider()
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
            )
            Text(text = "Pagar Netflix            ")
            Text(text = "100")
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
            )
            Text(text = "Pagar Universidad            ")
            Text(text = "6000")
        }
    }
}