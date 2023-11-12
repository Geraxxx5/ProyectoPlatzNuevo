package com.example.proyectoplatzapplication.ui.registro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import android.app.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme
import com.google.api.Context
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegistroDeGastos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPlatzApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CrearPantallaRegistro()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CrearPantallaRegistro() {
    val gastos = mutableStateListOf<Gasto>()
    val fechaSeleccionada = mutableStateOf("")
    var categoriaSeleccionada = remember { mutableStateOf("Uso Personal") }
    val descripcion = mutableStateOf("")
    val cantidadGastada = mutableStateOf("")
    var snackbarVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "REGISTRO DE GASTOS",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 16.dp) // Agrega espacio de padding en la parte derecha
                    .padding(16.dp) // Agrega espacio de padding alrededor del título
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar gasto",
                modifier = Modifier
                    .padding(1.dp)

                    .clickable {
                        snackbarVisible = true
                    }
            )
        }


        CustomDatePicker(selectedDate = fechaSeleccionada, modifier = Modifier.padding(16.dp))

        DropdownMenu(
            expanded = false,
            onDismissRequest = { },
            modifier = Modifier.padding(16.dp)
        ) {
            androidx.compose.material.DropdownMenuItem(onClick = { categoriaSeleccionada = mutableStateOf("Comida") }) {
                Text("Comida")
            }
            androidx.compose.material.DropdownMenuItem(onClick = { categoriaSeleccionada = mutableStateOf("Deudas") }) {
                Text("Deudas")
            }
            androidx.compose.material.DropdownMenuItem(onClick = { categoriaSeleccionada = mutableStateOf("Emergencia") }) {
                Text("Emergencia")
            }
            androidx.compose.material.DropdownMenuItem(onClick = { categoriaSeleccionada = mutableStateOf("Uso Personal") }) {
                Text("Uso Personal")
            }
        }

        TextField(
            value = descripcion.value,
            onValueChange = { descripcion.value = it },
            label = { Text(text = "Descripción/Uso") },
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = cantidadGastada.value,
            onValueChange = { cantidadGastada.value = it },
            label = { Text(text = "Gasto") },
            modifier = Modifier.padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        LazyColumn {
            items(gastos) { gasto ->
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(text = gasto.fecha)
                    Text(text = gasto.categoria)
                    Text(text = gasto.descripcion)
                    Text(text = gasto.cantidadGastada)
                }
            }
        }

        Text(
            text = "Total gastado: ${calcularTotalGastado(gastos)}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        if (snackbarVisible) {
            Snackbar(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Gasto agregado correctamente")
            }
            LaunchedEffect(snackbarVisible) {
                delay(2000)
                snackbarVisible = false
            }
        }
    }
}

data class Gasto(
    val fecha: String,
    val categoria: String,
    val descripcion: String,
    val cantidadGastada: String)

fun calcularTotalGastado(gastos: List<Gasto>): String {
    var total = 0.0

    for (gasto in gastos) {
        total += gasto.cantidadGastada
    }

    return total.toString()
}

private operator fun Double.plusAssign(cantidadGastada: String) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(selectedDate: MutableState<String>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                selectedDate.value = dateFormat.format(selectedCalendar.time)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    OutlinedButton(
        onClick = {
            showDatePicker()
        },
        modifier = modifier
    ) {
        Text(text = "Select Date")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun showDatePicker(selectedDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate.value = dateFormat.format(selectedCalendar.time)
        },
        year,
        month,
        day
    )

    datePickerDialog.show()
}


/*@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier,
    content: @Composable (Snackbar.SnackbarLayout) -> Unit
) {
    val host = rememberSnackbarHostState()

    LaunchedEffect(host) {
        host.showSnackbar(
            message = "",
            actionLabel = "Dismiss"
        )
    }

    SnackbarHost(
        hostState = host,
        snackbar = content
    )
}*/

@Preview
@Composable
fun PreviewPantallaRegistro() {
    CrearPantallaRegistro()
}