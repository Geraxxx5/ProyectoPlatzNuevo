package com.example.proyectoplatzapplication.Navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Output
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.proyectoplatzapplication.R
import com.example.proyectoplatzapplication.ui.Ahorros.AhorrosScreen
import com.example.proyectoplatzapplication.ui.ExpenseReport.ReporteGastos
import com.example.proyectoplatzapplication.ui.Home.Home
import com.example.proyectoplatzapplication.ui.Login.MainLogin
import com.example.proyectoplatzapplication.ui.Reminders.ReminderApp
import com.example.proyectoplatzapplication.ui.registro.CrearPantallaRegistro
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
fun DrawerExample(viewModel: NavigationFirebase = androidx.lifecycle.viewmodel.compose.viewModel()){
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val routes = listOf("Home", "Reportes", "Recordatorios", "Gastos", "Ahorros", "Contactos", "Calculadora")
    val selectedRoute = remember { mutableStateOf(routes[0]) }
    val icons = listOf(R.drawable.homeee,R.drawable.reportee , R.drawable.recordatorio, R.drawable.gastoss, R.drawable.ahorro, R.drawable.contacto, R.drawable.calculadora)
    if(viewModel.UserUiState.nombre == "" && viewModel.UserUiState.email == ""){
        viewModel.getShowDetails()
    }

    if(viewModel.UserUiState.loading) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = "https://assets.metrolatam.com/cl/2015/11/09/captura-de-pantalla-2015-11-09-a-las-09-20-49-1-1200x800.jpg",
                            contentDescription = "",
                            Modifier
                                .size(100.dp)
                                .wrapContentSize(Alignment.Center)
                                .clip(CircleShape)
                        )
                    }
                    Text(
                        text = "${viewModel.UserUiState.nombre}",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp
                    )
                    Text(
                        text = "${viewModel.UserUiState.email}",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Divider()
                    routes.forEachIndexed { index, route ->
                        NavigationDrawerItem(
                            label = { Text(text = route) },
                            selected = route == selectedRoute.value,
                            icon = {
                                Image(
                                    painter = painterResource(icons[index]),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
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
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    TopAppBar(
                        title = { Text(currentRoute ?: "Drawer Example") },
                        colors = TopAppBarDefaults.largeTopAppBarColors(Color(0xFFF4FEE9)),
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
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.singOut()
                                context.startActivity(Intent(context, MainLogin::class.java))
                                activity?.finish()
                            }) {
                                Icon(Icons.Filled.Output, contentDescription = "")
                            }
                        }
                    )

                },
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = "Home"
                    ) {
                        composable(route = "Home") {
                            Home(navController = navController)
                        }
                        composable(route = "Reportes") {
                            ReporteGastos(navController = navController)
                        }
                        composable(route = "Recordatorios") {
                            ReminderApp(navController = navController)
                        }
                        composable(route = "Gastos") {
                            CrearPantallaRegistro(navController = navController)
                        }
                        composable(route = "Ahorros"){
                            AhorrosScreen(navController = navController)
                        }
                    }
                }
            )
        }
    }else{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPlatzApplicationTheme {
        DrawerExample()
    }
}