package com.example.proyectoplatzapplication.ui.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectoplatzapplication.R

val menu = listOf(
    listOf("Ahorros", R.drawable.ahorro),
    listOf("Gastos", R.drawable.gastoss),
    listOf("Reportes", R.drawable.reportee),
    listOf("Contactos", R.drawable.contacto)
)

@Composable
fun Home(navController: NavController,viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    if(viewModel.HomeUiState.values.isEmpty()){
        viewModel.getDatatoShow()
    }

    if(viewModel.HomeUiState.loading) {
        Column {
            Spacer(modifier = Modifier.height(70.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(menu.size) { index ->
                    val name = menu[index][0] as String
                    val image = painterResource(id = menu[index][1] as Int)
                    Cards(image, name, navController)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Distribucion de Gastos del dia",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
            Divider()
            val booking = listOf(
                listOf("Gastos de Alimentacion", viewModel.HomeUiState.values[0]),
                listOf("Gastos Uso Personal", viewModel.HomeUiState.values[1]),
                listOf("Deudas", viewModel.HomeUiState.values[2]),
                listOf("Emergencias", viewModel.HomeUiState.values[3])
            )
            booking.forEachIndexed { index, p ->
                Text(
                    text = "${p[0]}:      ${p[1]}",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Divider()
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cards(image: Painter, name: String,navController: NavController) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .height(150.dp),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(Color(0xFFEAFFD6)),
            onClick = {navController.navigate(name)}
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = image,
                    contentDescription = "",
                    Modifier
                        .size(70.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                Text(
                    text = name,
                    Modifier.align(Alignment.BottomCenter),
                    fontSize = 20.sp
                )
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun HomePreview(){
    val navController = rememberNavController()
    Home(navController)
}