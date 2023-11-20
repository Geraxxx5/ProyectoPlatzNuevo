package com.example.proyectoplatzapplication.ui.Contactos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.net.Inet4Address

data class MyMessages(val id: Int, val title: String, val body: String, val address: String)

private val message: List<MyMessages> = listOf(
    MyMessages(1, "BANRURAL", "4200-3224","Cdad.Guatemala\", \"7A Avenida 19-28\", \"Mixco\", \"6A Calle 4-36\",\"Huehuetenango\", \"Calz Kaibil Balam"),
    MyMessages(2, "G&T", "5324-3787","Cdad.Guatemala\", \"Mercado La Villa de Guadalupe\", \"Guatemala\", \"7A Calle 6-23\",\"Mixco\", \"11 calle 4-13"),
    MyMessages(3, "BANCO INMOBILIARIO, S. A.", "4785-2017","Cdad.Guatemala\",\"Vista Hermosa, Carril Auxiliar\",\"Guatemala\", \"9A Calle 2-42\",\"Zacapa\", \"13 calle 5-12"),
    MyMessages(4, "BANCO DE LOS TRABAJADORES", "4525-4200","Cdad.Guatemala\",\"Vista Hermosa\",\"Guatemala\", \"7A Calle 2-47\",\"GUATEMALA\", \"12 calle 5-14"),
    MyMessages(5, "BANCO INDUSTRIAL, S. A.", "4312-2312","Cdad.Guatemala\",\"ZONA 8\",\"Guatemala\", \"7A Calle 2-47\",\"GUATEMALA\", \"12 calle 5-14"),
    MyMessages(6, "BANCO DE DESARROLLO RURAL, S. A.", "5623-2314","Cdad.Guatemala\",\"ZONA 7\",\"Guatemala\", \"4A Calle 2-23\",\"GUATEMALA\", \"10 calle 5-12"),
    MyMessages(7, "BANCO INTERNACIONAL, S. A.\n", "5617-2314","Cdad.Guatemala\",\"ZONA 21\",\"Guatemala\", \"5A Calle 2-47\",\"GUATEMALA\", \"9 calle 5-23"),
    MyMessages(8, "BAM", "4613-2714","Cdad.Guatemala\",\"ZONA 17\",\"Guatemala\", \"7A Calle 2-47\",\"GUATEMALA\", \"14 calle 7-22"),
    MyMessages(9, "CITY", "4774-2214","Cdad.Guatemala\",\"ZONA 14\\\",\"Guatemala\", \"2A Calle 2-43\",\"GUATEMALA\", \"7 calle 5-20")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPlatzApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "contact_list") {
                    composable("contact_list") {
                        MyMessages(navController, message)
                    }
                    composable("contact_detail/{id}") { backStackEntry ->
                        val contactId = backStackEntry.arguments?.getString("id")
                        val contact = message.find { it.id == contactId?.toInt() }
                        if (contact != null) {
                            ContactDetail(contact = contact)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyComponent(messages: MyMessages, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        //MyImage()
        MyTexts(messages)
    }
}

//@Composable
//fun MyImage() {
    //Image(
      //  painterResource(R.drawable.ic_launcher_foreground),
        //"Mi imagen de prueba",
        //modifier = Modifier
          //  .clip(CircleShape)
            //.background(MaterialTheme.colorScheme.primary)
            //.size(64.dp)
    //)
//}

@Composable
fun MyTexts(messages: MyMessages) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        MyText(messages.title, MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.size(4.dp))
        MyText(messages.body, MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun MyText(text: String, color: Color) {
    Text(text, color = color)
}

@Composable
fun MyMessagesItem(navController: NavController, message: MyMessages) {
    MyComponent(message) {
        navController.navigate("contact_detail/${message.id}")
    }
}

@Composable
fun MyMessages(navController: NavController, messages: List<MyMessages>) {
    LazyColumn {
        items(messages) { message ->
            MyMessagesItem(navController, message)
        }
    }
}

@Composable
fun ContactDetail(contact: MyMessages) {
    Column {
        Text(text = "Nombre: ${contact.title}")
        Text(text = "Teléfono: ${contact.body}")
        Text(text = "Dirección: ${contact.address}")

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewComponent(){
    ProyectoPlatzApplicationTheme {
        MyMessages(rememberNavController(), message)
    }
}
