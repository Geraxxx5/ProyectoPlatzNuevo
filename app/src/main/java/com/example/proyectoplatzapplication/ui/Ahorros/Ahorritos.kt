package com.example.proyectoplatzapplication.ui.Ahorros

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectoplatzapplication.R
import com.example.proyectoplatzapplication.ui.registro.CrearPantallaRegistro
import com.example.proyectoplatzapplication.ui.registro.Gasto
import com.example.proyectoplatzapplication.ui.registro.RegistroGastosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AhorrosScreen(navController: NavController, viewModel: AhorrosViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var openDialog by remember { mutableStateOf(false) }
    var ahorros by remember { mutableStateOf(listOf<Ahorro>()) }
    var selectedAhorro by remember { mutableStateOf<Ahorro?>(null) }

    LaunchedEffect(key1 = viewModel) {
        val values = viewModel.obtainSavings()
        withContext(Dispatchers.Main) {
            for (i in values) {
                Log.d("Ahorros", "Fecha: ${i.objective}")
                ahorros = ahorros + Ahorro(i.objective!!,i.description!!,i.goal!!.toInt(),i.current!!.toInt())
            }
        }
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ahorros",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF12950E),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 30.sp
                )
            )
            IconButton(onClick = { openDialog = true }) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.addddd),
                    contentDescription = "Agregar Ahorro",
                    modifier = Modifier
                        .size(45.dp),
                    tint = Color.Unspecified
                )
            }
        }
        ahorros.forEach { ahorro ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(Color(0xFFE0FDC3)),
                onClick = { selectedAhorro = ahorro }
            ) {
                Text(text = ahorro.objetivo , style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp ))
            }
        }
        if (openDialog) {
            NuevoAhorroDialog(onClose = { openDialog = false }, onAhorroCreated = {nuevoAhorro ->
                ahorros = ahorros + nuevoAhorro},viewModel = viewModel)
        }
    }

    selectedAhorro?.let { ahorro ->
        AhorroDetails(
            ahorro = ahorro,
            onAhorroUpdated = { updatedAhorro ->
                ahorros = ahorros.map { if (it == ahorro) updatedAhorro else it }
            },
            onBack = { selectedAhorro = null },
            viewModel
        )
    }
}

@Composable
fun NuevoAhorroDialog(onClose: () -> Unit, onAhorroCreated: (Ahorro) -> Unit, viewModel: AhorrosViewModel) {
    var objetivo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var meta by remember { mutableStateOf("") }
    var actual by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(text = "Nueva meta de ahorro", style = TextStyle(
            fontWeight = FontWeight.Bold,
            color = Color(0xFF12950E),
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp
        )) },
        text = {
            Column {
                TextField(value = objetivo, onValueChange = { objetivo = it }, label = { Text("Objetivo") })
                TextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                TextField(value = meta, onValueChange = { meta = it }, label = { Text("Meta") })
                TextField(value = actual, onValueChange = { actual = it }, label = { Text("Actual") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAhorroCreated(Ahorro(objetivo, descripcion, meta.toInt(), actual.toInt()))
                viewModel.createNewSaving(Savings(objetivo,descripcion,meta,actual))
                onClose()
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onClose, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun AhorroDetails(ahorro: Ahorro, onAhorroUpdated: (Ahorro) -> Unit, onBack: () -> Unit,viewModel: AhorrosViewModel) {
    var adding by remember { mutableStateOf(false) }
    var amountToAdd by remember { mutableStateOf("") }
    var currentAmount by remember { mutableStateOf("") }
    currentAmount = ahorro.actual.toString()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "META: ${ahorro.meta}", style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontSize = 25.sp
            ))
            Text(text = "Objetivo: ${ahorro.objetivo}", style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontSize = 25.sp
            ))
            Text(text = "Descipción: ${ahorro.descripcion}", style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp
            ))
            Text(text = "Actual: $currentAmount",style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontSize = 25.sp
            ))
            CircularProgressIndicator(
                progress = ahorro.actual.toFloat() / ahorro.meta,
                color = Color.Green,
                strokeWidth = 15.dp
            )
            if (adding) {
                TextField(value = amountToAdd, onValueChange = { amountToAdd = it }, label = { Text("Cantidad a agregar") })
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val newAmount = ahorro.actual + amountToAdd.toInt()
                            val updatedAhorro = Ahorro(
                                objetivo = ahorro.objetivo,
                                descripcion = ahorro.descripcion,
                                meta = ahorro.meta,
                                actual = newAmount
                            )
                            onAhorroUpdated(updatedAhorro)
                            viewModel.updateSavings(ahorro.objetivo,ahorro.meta.toString(),newAmount.toString())
                            currentAmount = newAmount.toString()
                            adding = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))
                    ) {
                        Text("Agregar")
                    }
                    Button(
                        onClick = { adding = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))
                    ) {
                        Text("Cancelar")
                    }
                }
            } else {
                Button(
                    onClick = { adding = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))
                ) {
                    Text("Agregar más dinero")
                }
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))
                ) {
                    Text("Atrás")
                }
            }
        }
    }
}

data class Ahorro(val objetivo: String, val descripcion: String, val meta: Int, val actual: Int)

@Preview
@Composable
fun PreviewPantallaAhorros() {
    val navController = rememberNavController()
    AhorrosScreen(navController)
}
