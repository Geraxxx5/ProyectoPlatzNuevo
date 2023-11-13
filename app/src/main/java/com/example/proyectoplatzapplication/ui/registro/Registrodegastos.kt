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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoplatzapplication.R
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme
import com.google.api.Context
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



data class Gasto(
    val fecha: String,
    val categoria: String,
    val descripcion: String,
    val cantidadGastada: String
)

class RegistroDeGastos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrearPantallaRegistro()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CrearPantallaRegistro() {
    val gastos = remember { mutableStateListOf<Gasto>() }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "REGISTRO DE GASTOS",
                modifier = Modifier.weight(2f),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF12950E),fontFamily = FontFamily.SansSerif,fontSize = 25.sp

                ))
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.addddd),
                        contentDescription = "Agregar registro",
                        modifier = Modifier
                            .size(45.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Fecha",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,fontFamily = FontFamily.SansSerif,fontSize = 16.sp),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Categoría",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,fontFamily = FontFamily.SansSerif,fontSize = 16.sp),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Descripción",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,fontFamily = FontFamily.SansSerif,fontSize = 16.sp),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Gasto",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,fontFamily = FontFamily.SansSerif,fontSize = 16.sp),
                modifier = Modifier.weight(1f)
            )
        }

        LazyColumn {
            items(gastos) { gasto ->
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = gasto.fecha, modifier = Modifier.weight(1f))
                    Text(text = gasto.categoria, modifier = Modifier.weight(1f))
                    Text(text = gasto.descripcion, modifier = Modifier.weight(1f))
                    Text(text = gasto.cantidadGastada, modifier = Modifier.weight(1f))
                }
            }
        }
    Box(modifier =Modifier.fillMaxWidth().background(Color(0xFFD2FEA8))){
        Text(
            text = "Total gastado Q: ${calcularTotalGastado(gastos)}",
            modifier = Modifier.padding(vertical = 16.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,fontFamily = FontFamily.SansSerif,fontSize = 20.sp)

            )}

        if (showDialog) {
            AgregarGastoDialog(
                gastos = gastos,
                onDismiss = { showDialog = false },
                onAgregarGasto = {
                    gastos.add(
                        Gasto(
                            fecha = it.fecha,
                            categoria = it.categoria,
                            descripcion = it.descripcion,
                            cantidadGastada = it.cantidadGastada
                        )
                    )
                    showDialog = false
                }
            )
        }
    }
}


@Composable
fun AgregarGastoDialog(
    gastos: List<Gasto>,
    onDismiss: () -> Unit,
    onAgregarGasto: (Gasto) -> Unit
) {
    val fechaSeleccionada = remember { mutableStateOf("") }
    val categoriaSeleccionada = remember { mutableStateOf("Uso Personal") }
    val descripcion = remember { mutableStateOf("") }
    val cantidadGastada = remember { mutableStateOf("") }
    val categorias = listOf("Comida", "Deudas", "Emergencia", "Uso Personal")
    var isDropdownMenuVisible by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Agregar gasto") },
        confirmButton = {
            Button(
                onClick = {
                    onAgregarGasto(
                        Gasto(
                            fecha = fechaSeleccionada.value,
                            categoria = categoriaSeleccionada.value,
                            descripcion = descripcion.value,
                            cantidadGastada = cantidadGastada.value
                        )
                    )
                    onDismiss()
                }
            ) {
                Text(text = "Agregar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = "Cancelar")
            }
        },
        text = {
            Column {
                CustomDatePicker(selectedDate = fechaSeleccionada)

                Button(
                    onClick = { isDropdownMenuVisible = true }
                ) {
                    Text(text = "Categoría: ${categoriaSeleccionada.value}")
                }

                DropdownMenu(
                    expanded = isDropdownMenuVisible,
                    onDismissRequest = { isDropdownMenuVisible = false },
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(onClick = {
                            categoriaSeleccionada.value = categoria
                            isDropdownMenuVisible = false
                        }) {
                            Text(categoria)
                        }
                    }
                }

                TextField(
                    value = descripcion.value,
                    onValueChange = { descripcion.value = it },
                    label = { Text(text = "Descripción/Uso") },
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                TextField(
                    value = cantidadGastada.value,
                    onValueChange = { cantidadGastada.value = it },
                    label = { Text(text = "Gasto") },
                    modifier = Modifier.padding(vertical = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    )
}


@Composable
fun CustomDatePicker(selectedDate: MutableState<String>) {
    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

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
                selectedDate.value = dateFormatter.format(selectedCalendar.time)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    Button(
        onClick = { showDatePicker() },
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(text = "Seleccionar fecha")
    }
}

fun calcularTotalGastado(gastos: List<Gasto>): Double {
    var total = 0.0

    for (gasto in gastos) {
        total += gasto.cantidadGastada.toDoubleOrNull() ?: 0.0
    }

    return total
}
@Preview
@Composable
fun PreviewPantallaRegistro() {
    CrearPantallaRegistro()
}