package com.example.proyectoplatzapplication.Navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.proyectoplatzapplication.ui.ExpenseReport.ReporteGastos
import com.example.proyectoplatzapplication.ui.Home.Home
import com.example.proyectoplatzapplication.ui.Reminders.Reminders
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPlatzApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawerExample()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerExample(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val routes = listOf("Home", "Reportes", "Recordatorios", "Gastos", "Ahorros", "Contactos", "Calculadora", "Configuraciones")
    val selectedRoute = remember { mutableStateOf(routes[0]) }
    var select = listOf(true,false,false,false,false,false,false,false)
    var mutableSelect = select.toMutableList()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center){
                    AsyncImage(model = "https://assets.metrolatam.com/cl/2015/11/09/captura-de-pantalla-2015-11-09-a-las-09-20-49-1-1200x800.jpg",
                        contentDescription = "",
                        Modifier
                            .size(100.dp)
                            .wrapContentSize(Alignment.Center)
                            .clip(CircleShape))
                }
                Text(text = "Nombre y apellido", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 25.sp)
                Text(text = "correo", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Divider()
                routes.forEach { route ->
                    NavigationDrawerItem(
                        label = { Text(text = route) },
                        selected = route == selectedRoute.value,
                        icon = { Icon(Icons.Filled.Home, contentDescription = "") },
                        onClick = {
                            navController.navigate(route)
                            selectedRoute.value = route
                            scope.launch { drawerState.close() }
                        }
                    )
                }

            }
        },
    ) {
        Scaffold(
            topBar = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                TopAppBar(
                    title = { Text(currentRoute ?: "Drawer Example") },
                    colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )

            },
            content = {
                NavHost(
                    navController = navController,
                    startDestination = "Home"
                ){
                    composable(route = "Home"){
                        Home(navController = navController)
                    }
                    composable(route = "Reportes"){
                        ReporteGastos(navController = navController)
                    }
                    composable(route = "Recordatorios"){
                        Reminders(navController = navController)
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPlatzApplicationTheme {
        DrawerExample()
    }
}