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
import kotlinx.coroutines.delay

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
    val categoriaSeleccionada = mutableStateOf("")
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
            DropdownMenuItem(onClick = { categoriaSeleccionada.value = "Uso Personal" }) {
                Text(text = "Uso Personal")
            }
            DropdownMenuItem(onClick = { categoriaSeleccionada.value = "Comida" }) {
                Text(text = "Comida")
            }
            DropdownMenuItem(onClick = { categoriaSeleccionada.value = "Deudas" }) {
                Text(text = "Deudas")
            }
            DropdownMenuItem(onClick = { categoriaSeleccionada.value = "Emergencia" }) {
                Text(text = "Emergencia")
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
            modifier = Modifier.padding(16.dp)
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
            text = "Total gastado: ${calcularTotalGastado()}",
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
    val cantidadGastada: String
)

fun calcularTotalGastado(): String {
    var total = 0.0

    for (gasto in gastos) {
        total += gasto.cantidadGastada.toDouble()
    }

    return total.toString()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    selectedDate: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedDate.value,
            onValueChange = { selectedDate.value = it },
            label = { Text(text = "Select Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            DatePicker(
                context = context,
                onDateSelected = { date ->
                    selectedDate.value = date.toString()
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun Snackbar(
    modifier: Modifier = Modifier,
    content: @Composable (SnackbarLayout) -> Unit
) {
    val host = rememberSnackbarHostState()

    LaunchedEffect(host) {
        host.showSnackbar(
            message = "Snackbar message",
            actionLabel = "Dismiss"
        )
    }

    SnackbarHost(
        hostState = host,
        snackbar = { snackbarData ->
            SnackbarLayout(
                modifier = modifier,
                snackbarData = snackbarData,
                action = {
                    Button(
                        onClick = { host.currentSnackbarData?.performAction() },
                    ) {
                        Text(snackbarData.actionLabel!!)
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun PreviewPantallaRegistro() {
    CrearPantallaRegistro()
}
