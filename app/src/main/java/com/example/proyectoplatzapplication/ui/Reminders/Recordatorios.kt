package com.example.proyectoplatzapplication.ui.Reminders

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectoplatzapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.min

@Composable
fun ReminderApp(navController: NavController) {
    val reminders = remember { mutableStateListOf<String>() }
    val showAddReminder = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(Calendar.getInstance()) }
    val amount = remember { mutableStateOf("") }
    val reminderText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recordatorios",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF12950E),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 30.sp
                )
            )
            IconButton(onClick = { showAddReminder.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.addddd),
                    contentDescription = "Agregar Recordatorio",
                    modifier = Modifier
                        .size(45.dp),
                    tint = Color.Unspecified
                )
            }
        }

        CalendarScreen()

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recordatorio",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, fontFamily = FontFamily.Serif, fontSize = 16.sp
                ),
                modifier = Modifier.weight(1f) )
            Text(
                text = "Monto",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, fontFamily = FontFamily.Serif, fontSize = 20.sp
                ),
                modifier = Modifier.weight(1f) )

            Text(
                text = "Fecha",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, fontFamily = FontFamily.Serif, fontSize = 20.sp
                ),
                modifier = Modifier.weight(1f) ) }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

        //CustomDatePicker(selectedDate = selectedDate)

        if (showAddReminder.value) {
            AddReminder(
                onAddReminder = { reminder ->
                    reminders.add(reminder)
                    showAddReminder.value = false
                },
                onCancel = { showAddReminder.value = false },
                selectedDate = selectedDate.value,
                amount = amount.value,
                reminderText = reminderText.value,
                onDateSelected = { date -> selectedDate.value = date },
                onAmountChanged = { newAmount -> amount.value = newAmount },
                onReminderTextChanged = { newText -> reminderText.value = newText }
            )
        }

        reminders.forEachIndexed { index, reminder ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = false,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            reminders.removeAt(index)
                        }

                    }
                )
                Text(
                    text = reminder,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun CustomDatePicker(selectedDate: Calendar) {
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
                //selectedDate.value = selectedCalendar
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    Button(
        onClick = { showDatePicker() },
        modifier = Modifier.padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))
    ) {
        Text(text = "Seleccionar fecha")
    }
}

@Composable
fun AddReminder(
    onAddReminder: (String) -> Unit,
    onCancel: () -> Unit,
    selectedDate: Calendar,
    amount: String,
    reminderText: String,
    onDateSelected: (Calendar) -> Unit,
    onAmountChanged: (String) -> Unit,
    onReminderTextChanged: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Agregar Recordatorio",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = reminderText,
            onValueChange = { newText -> onReminderTextChanged(newText) },
            label = { Text("Recordatorio") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = amount,
            onValueChange = { newAmount -> onAmountChanged(newAmount) },
            label = { Text("Monto") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CustomDatePicker(selectedDate)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { onAddReminder("$reminderText  $amount  ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)}") },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8)),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Agregar")
            }
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD2FEA8))
            ) {
                Text(text = "Cancelar")
            }
        }
    }
}

@Composable
fun CalendarScreen() {
    val calendar = Calendar.getInstance()
    val month = calendar.get(Calendar.MONTH) + 1
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val year = calendar.get(Calendar.YEAR)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(text = "Fecha:  $dayOfMonth / $month / $year ", fontSize = 20.sp)
        }
        items((daysInMonth - 1) / 7 + 1) { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val startDay = week * 7
                val endDay = min(startDay + 7, daysInMonth)
                for (day in startDay until endDay) {
                    Text(
                        text = (day + 1).toString(),
                        modifier = Modifier.padding(4.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
