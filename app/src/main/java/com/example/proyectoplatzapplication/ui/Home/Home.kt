package com.example.proyectoplatzapplication.ui.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

val menu = listOf(
    listOf("Ahorros", Icons.Filled.MailOutline),
    listOf("Registro de gastos",Icons.Filled.Info),
    listOf("Reportes",Icons.Filled.Face),
    listOf("Contactos",Icons.Filled.AccountBox)
)

@Composable
fun Home(navController: NavController){
    Column {
        Spacer(modifier = Modifier.height(70.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ){
            items(menu.size){index ->
                val name = menu[index][0] as String
                val image = menu[index][1] as ImageVector
                Cards(image,name)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Distribucion de Gastos del dia",Modifier.fillMaxWidth(), textAlign = TextAlign.Start, fontSize = 20.sp)
        Divider()

    }
}

@Composable
fun Cards(image: ImageVector, name:String){
    Card( shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .height(150.dp)
    ) {
        Box(Modifier.fillMaxSize()){
            Image(imageVector = image, contentDescription = "",
                Modifier
                    .size(70.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center))
            Text(text = name,Modifier.align(Alignment.BottomCenter), fontSize = 20.sp)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview(){
    val navController = rememberNavController()
    Home(navController)
}