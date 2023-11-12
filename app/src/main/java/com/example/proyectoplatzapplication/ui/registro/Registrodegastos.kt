package com.example.proyectoplatzapplication.ui.registro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.*
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
import com.google.android.material.snackbar.Snackbar
import com.google.api.Context
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar


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
    val categoriaSeleccionada = remember { mutableStateOf("Uso Personal") }
    val descripcion = mutableStateOf("")
    val cantidadGastada = mutableStateOf("")
    var snackbarVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "REGISTRO DE GASTOS",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar gasto",
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    snackbarVisible = true
                }
        )

        CustomDatePicker(selectedDate = fechaSeleccionada, modifier = Modifier.padding(16.dp))

        DropdownMenu(
            expanded = false,
            onDismissRequest = { },
            modifier = Modifier.padding(16.dp)
        ) {
            DropdownMenuItem(onClick = { categoriaSeleccionada = "Uso Personal" }) {
                Text("Uso Personal")
            }

            DropdownMenuItem(onClick = { categoriaSeleccionada = "Comida" }) {
                Text("Comida")
            }

            DropdownMenuItem(onClick = { categoriaSeleccionada = "Deudas" }) {
                Text("Deudas")
            }

            DropdownMenuItem(onClick = { categoriaSeleccionada = "Emergencia" }) {
                Text("Emergencia")
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
    val cantidadGastada: Double
)

fun calcularTotalGastado(gastos: List<Gasto>): String {
    var total = 0.0

    for (gasto in gastos) {
        total += gasto.cantidadGastada
    }

    return total.toString()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    selectedDate: MutableState<String>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    TextField(
        value = selectedDate.value,
        onValueChange = { selectedDate.value = it },
        label = { Text(text = "Select Date") },
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                showDatePicker(context, selectedDate)
            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
private fun showDatePicker(context: Context, selectedDate: MutableState<String>) {
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

@Composable
fun Snackbar(
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
}

@Preview
@Composable
fun PreviewPantallaRegistro() {
    CrearPantallaRegistro()
}